/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.integration.leatherback;

import com.integration.leatherback.entities.PaymentLink;
import com.integration.leatherback.entities.PaymentLinkRepository;
import com.integration.leatherback.entities.PaymentTransaction;
import com.integration.leatherback.entities.PaymentTransactionRepository;
import com.integration.leatherback.models.CreateLeatherbackPaymentLinkResponse;
import com.integration.leatherback.models.CreatePaymentLinkRequest;
import com.integration.leatherback.models.LeatherbackWebhookRequest;
import com.integration.leatherback.models.WebhookData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.cloud.openfeign.encoding.HttpEncoding.CONTENT_TYPE;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author daviestobialex
 */
@RestController
@RequestMapping("/integration")
public class ControllerResources {

    private final LeatherbackService service;
    private final ProfiledConfigurations configuration;
    private final PaymentLinkRepository repository;
    private final PaymentTransactionRepository transactionRepository;

    @Autowired
    public ControllerResources(LeatherbackService service, ProfiledConfigurations config,
            PaymentLinkRepository repository, PaymentTransactionRepository transactionRepository) {
        this.service = service;
        this.configuration = config;
        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }

    @Operation(description = "creates payment link with leatherback and persists key information such as alias and link, against the eternal id")
    @Transactional(rollbackOn = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "create/payment/link", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createPaymentLink(@RequestBody CreatePaymentLinkRequest request) {

        // post request
        CreateLeatherbackPaymentLinkResponse response = service
                .createPaymentLink(configuration.getKey(),
                        request.getRequest());

        if (response.isSuccess()) {
            // persist response

            repository.save(new PaymentLink(request.getExternalId(),
                    response.getValue().getAlias(), request.getCallbackUrl(),
                    response.getValue().getLink()));
        }
    }

    @Operation(description = "get payment link attached to an external id")
    @GetMapping(path = "payment/link/{externalId}")
    public PaymentLink getLink(@PathVariable String externalId) {

        Optional<PaymentLink> paymentLinkRecord = repository.findByExternalId(externalId);

        return paymentLinkRecord
                .orElseThrow(() -> new EntityNotFoundException("payment link with external id ".concat(externalId)));
    }

    @Operation(description = "webhook event resource that routes request to callback URLs attached to external id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = "webhook/collection/only", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void leatherbackWebhook(@RequestBody LeatherbackWebhookRequest event) {

        // persist alias first
        PaymentTransaction transaction = new PaymentTransaction(event.getData());
        transaction.setEvent(event.getEvent());
        transaction = transactionRepository.save(transaction);

        Optional<PaymentLink> paymentRecord = repository
                .findByAlias(event.getData().getMetadata().getAlias());

        if (paymentRecord.isPresent()) {

            transaction.setPaymentLink(paymentRecord.get());

            transaction = transactionRepository.save(transaction);

            final ResponseEntity response = executeCallback(paymentRecord.get().getCallbackUrl(),
                    event);

            update(transaction, response);

            if (!response.getStatusCode().is2xxSuccessful()) {
                // have a retry policy
                scheduleRetryTask(transaction.getId(),
                        transaction.getPaymentLink().getCallbackUrl());
            }

        }
    }

    /**
     * execute callback request
     *
     * @param callbackURL
     * @param event
     * @return
     */
    public ResponseEntity executeCallback(String callbackURL, LeatherbackWebhookRequest event) {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<LeatherbackWebhookRequest> httpRequestEntity = new HttpEntity<>(event, headers);

        return template.exchange(callbackURL,
                HttpMethod.POST, httpRequestEntity, String.class);
    }

    /**
     * schedule retry task
     *
     * @param transactionId
     * @param callbackURL
     */
    public void scheduleRetryTask(Long transactionId, String callbackURL) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {

            @Override
            public void run() {

                Optional<PaymentTransaction> transactionRecord = transactionRepository
                        .findById(transactionId);

                WebhookData data = transactionRecord.get();

                LeatherbackWebhookRequest request
                        = new LeatherbackWebhookRequest(transactionRecord.get().getEvent(),
                                data);

                final ResponseEntity response = executeCallback(callbackURL, request);

                update(transactionRecord.get(), response);
            }

        }, 30000, 50000); // Schedule the retry task to run every 5 seconds (adjust as needed)
    }

    private void update(PaymentTransaction transaction, ResponseEntity response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            transaction.setCanRetry(true);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            transaction.setCanRetry(false);
        }

        transaction.setStatusCode(response.getStatusCode().value());

        transactionRepository.save(transaction);
    }
}
