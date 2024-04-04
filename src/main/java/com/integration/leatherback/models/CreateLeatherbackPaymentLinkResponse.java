/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.models;

import lombok.Data;

/**
 * leather response object for payment link creation
 *
 * @author daviestobialex
 */
@Data
public class CreateLeatherbackPaymentLinkResponse {

    private String instance;

    private String detail;

    private String error;

    private String message;

    private String type;

    private String title;

    private PaymentLinkResponseValue value;

    private boolean isSuccess;

    private String status;
}
