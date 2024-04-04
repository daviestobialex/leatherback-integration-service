/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data;

/**
 * Service operation request object to create a payment link with leather back
 *
 * @author daviestobialex
 */
@Data
public class CreatePaymentLinkRequest implements Serializable {

    /**
     * callback URL to be triggered processing event to client
     */
    @NotBlank(message = "callbackUrl can not be empty, null or blank")
    @Schema(description = "callback URL to be triggered processing event to client")
    private String callbackUrl;

    /**
     * external id of customer linked to created payment link
     */
    @NotBlank(message = "externalId can not be empty, null or blank")
    @Schema(description = "external id of customer linked to created payment link")
    private String externalId;

    /**
     * leatherback payment request data object
     */
    @NotBlank(message = "request can not be null")
    @Schema(description = "leatherback payment request data object")
    private CreateLeatherbackPaymentLinkRequest request;

}
