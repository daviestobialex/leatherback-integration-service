/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.models;

import com.integration.leatherback.enums.AmountType;
import com.integration.leatherback.enums.LinkType;
import java.io.Serializable;
import lombok.Data;

/**
 * create leatherback payment link request object
 *
 * @author daviestobialex
 */
@Data
public class CreateLeatherbackPaymentLinkRequest implements Serializable {

    private AmountType amountType;

    /**
     * short name for the payment link
     */
    private String name;

    /**
     * payment link description
     */
    private String description;

    private LinkType linkType;

    /**
     * amount information
     */
    private AmountInfo amountInfo;
}
