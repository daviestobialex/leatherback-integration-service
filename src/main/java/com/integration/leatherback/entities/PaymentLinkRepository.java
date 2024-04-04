/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.integration.leatherback.entities;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daviestobialex
 */
@Repository
public interface PaymentLinkRepository extends CrudRepository<PaymentLink, Long> {

    Optional<PaymentLink> findByExternalId(String externalId);

    Optional<PaymentLink> findByAlias(String alias);
}
