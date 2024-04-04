/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.integration.leatherback.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 *
 * @author daviestobialex
 */
public enum AmountType {

    STATIC(0), DYNAMIC(1);

    @Getter
    @JsonValue
    int type;

    private AmountType(int type) {
        this.type = type;
    }

}
