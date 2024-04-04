/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.integration.leatherback;

import com.integration.leatherback.models.CreateLeatherbackPaymentLinkRequest;
import com.integration.leatherback.models.CreateLeatherbackPaymentLinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A Leatherback Integration Service using feign client
 *
 * @author daviestobialex
 */
@FeignClient(name = "leatherback-integration", url = " https://laas.leatherback.co")
public interface LeatherbackService {

    /**
     * creates a payment link key component to watch out for on a success
     * response is the alias
     *
     * https://accessleatherback.atlassian.net/wiki/spaces/LAD/pages/1147159/Leatherback+Payment+Link+Integration
     *
     * @param authorization authorization header
     * @param request json body
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/api/payment/payment-link")
    CreateLeatherbackPaymentLinkResponse createPaymentLink(
            @RequestHeader("X-API") String authorization, @RequestBody CreateLeatherbackPaymentLinkRequest request);
}
