package com.arom.polisee.domain.repository;

import com.arom.polisee.domain.domain.PolicyDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PolicyDetailRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(PolicyDetail policyDetail) {
        em.persist(policyDetail);
    }
}
