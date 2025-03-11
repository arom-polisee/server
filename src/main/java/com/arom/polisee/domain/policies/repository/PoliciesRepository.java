package com.arom.polisee.domain.policies.repository;

import com.arom.polisee.domain.policies.entity.Policies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PoliciesRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Policies policy) {
        em.persist(policy);
    }

    public void saveAll(List<Policies> policies) {
        for (Policies policy : policies) save(policy);
        em.flush();

    }

    public Policies findByPolicyId(String policyId) {
        return em.find(Policies.class, policyId);
    }
}
