/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.integration.leatherback.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import static com.integration.leatherback.IntegerationServiceToLeatherbackApplication.FULL_DATE_TIME;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
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
@Table(name = "leatherback_payment_links")
@SuppressWarnings("PersistenceUnitPresent")
public class PaymentLink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true, length = 20)
    protected String externalId;

    @Column(unique = true, length = 20)
    protected String alias;

    @Column(unique = true, length = 100)
    protected String link;

    @Column(length = 100)
    protected String callbackUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FULL_DATE_TIME)
    @JsonIgnore
    @Column(insertable = false, updatable = true)
    @UpdateTimestamp
    protected Timestamp lastModified;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FULL_DATE_TIME)
    @CreationTimestamp
    protected Timestamp dateCreated;

    public PaymentLink(String externalId, String alias) {
        this.externalId = externalId;
        this.alias = alias;
    }

    public PaymentLink(String externalId, String alias, String callbackUrl) {
        this.externalId = externalId;
        this.alias = alias;
        this.callbackUrl = callbackUrl;
    }

    public PaymentLink(String externalId, String alias, String callbackUrl, String link) {
        this.externalId = externalId;
        this.alias = alias;
        this.callbackUrl = callbackUrl;
        this.link = link;
    }
}
