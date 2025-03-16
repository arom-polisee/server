package com.arom.polisee.domain.policy_requirements.repository;

import com.arom.polisee.domain.policy_requirements.entity.PolicyRequirements;
import com.nimbusds.openid.connect.sdk.assurance.Policy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
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
    }
    public Optional<PolicyRequirements> findById(String id) {
        return Optional.ofNullable(em.find(PolicyRequirements.class, id));
    }
}
