/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.models;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author daviestobialex
 */
@Data
public class AmountInfo implements Serializable {

    private Double amount;

    private String currencyCode;
}
