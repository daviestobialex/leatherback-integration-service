/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daviestobialex
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeatherbackWebhookRequest implements Serializable {

    @JsonProperty("Event")
    private String Event;

    @JsonProperty("Data")
    private WebhookData Data;
}
