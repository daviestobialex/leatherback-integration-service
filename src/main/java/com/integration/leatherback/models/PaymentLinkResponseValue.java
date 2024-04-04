/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.models;

import com.integration.leatherback.enums.AmountType;
import com.integration.leatherback.enums.LinkType;
import lombok.Data;

/**
 * Value object
 *
 * @author daviestobialex
 */
@Data
public class PaymentLinkResponseValue {

    private AmountType amountType;

    private String link;

    private String description;

    private AmountInfo amountInfo;

    private String supportEmail;

    private String environment;

    private String createdDate;

    private String name;

    private String alias;

    private String logo;

    private LinkType linkType;

    private String id;

    private String status;

}
