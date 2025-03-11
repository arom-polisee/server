package com.arom.polisee.domain.policy_requirements.repository;

import com.arom.polisee.domain.policy_requirements.entity.PolicyRequirements;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PolicyRequirementsRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(PolicyRequirements policyRequirements) {
        em.persist(policyRequirements);
    }

    public void saveAll(List<PolicyRequirements> policyRequirements) {
        for (PolicyRequirements policyRequirement : policyRequirements) {
            save(policyRequirement);
        }
        em.flush();
    }
    public PolicyRequirements findById(String id) {
        return em.find(PolicyRequirements.class, id);
    }
}
