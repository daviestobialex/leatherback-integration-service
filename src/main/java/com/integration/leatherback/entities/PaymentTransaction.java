/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import static com.integration.leatherback.IntegerationServiceToLeatherbackApplication.FULL_DATE_TIME;
import com.integration.leatherback.models.WebhookData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author daviestobialex
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "leatherback_payment_transaction")
@SuppressWarnings("PersistenceUnitPresent")
public class PaymentTransaction extends WebhookData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(length = 50, nullable = false)
    private String event;

    @JoinColumn(unique = false, nullable = true)
    @OneToOne
    private PaymentLink paymentLink;

    private int retried;

    private int statusCode;

    private boolean canRetry;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FULL_DATE_TIME)
    @JsonIgnore
    @Column(insertable = false, updatable = true)
    @UpdateTimestamp
    protected Timestamp lastModified;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FULL_DATE_TIME)
    @CreationTimestamp
    protected Timestamp dateCreated;

    public PaymentTransaction(WebhookData data, PaymentLink linkedPayment) {
        this.retried = 0;
        paymentLink = linkedPayment;
        this.narration = data.getNarration();
        this.amount = data.getAmount();
        this.amountPaid = data.getAmountPaid();
        this.appFee = data.getAppFee();
        this.channelType = data.getChannelType();
        this.currency = data.getCurrency();
        this.environment = data.getEnvironment();
        this.reference = data.getReference();
        this.requestSource = data.getRequestSource();
        this.paymentStatus = data.getPaymentStatus();
    }

    public PaymentTransaction(WebhookData data) {
        this.retried = 0;
        this.narration = data.getNarration();
        this.amount = data.getAmount();
        this.amountPaid = data.getAmountPaid();
        this.appFee = data.getAppFee();
        this.channelType = data.getChannelType();
        this.currency = data.getCurrency();
        this.environment = data.getEnvironment();
        this.reference = data.getReference();
        this.requestSource = data.getRequestSource();
        this.paymentStatus = data.getPaymentStatus();
    }
}
