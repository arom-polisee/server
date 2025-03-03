package com.arom.polisee.domain.repository;

import com.arom.polisee.domain.domain.Policy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PolicyRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Policy policy) {
        em.persist(policy);
    }

    public void saveAll(List<Policy> policies) {
        for (Policy policy : policies) {
            save(policy);
        }
    }

    public Policy findByPolicyId(String policyId) {
        return em.find(Policy.class, policyId);
    }
}
