/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daviestobialex
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookData implements Serializable {

    @Lob
    @JsonProperty("Narration")
    protected String narration;

    @Column(unique = true, length = 80)
    @JsonProperty("Reference")
    protected String reference;

    @Column(precision = 2)
    @JsonProperty("AmountPaid")
    protected BigDecimal amountPaid;

    @Column(precision = 2)
    @JsonProperty("Amount")
    protected BigDecimal amount;

    @JsonProperty("Currency")
    protected String currency;

    @Transient
    @JsonProperty("Metadata")
    protected Metadata metadata;

    @Transient
    @JsonProperty("Environment")
    protected String environment;

    @JsonProperty("RequestSource")
    protected String requestSource;

    @JsonProperty("AppFee")
    @Column(precision = 2)
    protected BigDecimal appFee;

    @JsonProperty("ChannelType")
    protected String channelType;

    @JsonProperty("PaymentStatus")
    protected String paymentStatus;
}
