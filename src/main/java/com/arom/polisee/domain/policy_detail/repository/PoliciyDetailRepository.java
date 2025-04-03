package com.arom.polisee.domain.policy_detail.repository;

import com.arom.polisee.domain.policy_detail.entity.PolicyDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class PoliciyDetailRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(PolicyDetail policy) {
        em.persist(policy);
    }

    public void saveAll(List<PolicyDetail> policies) {
        for (PolicyDetail policy : policies) save(policy);
    }

    public PolicyDetail findByPolicyId(String policyId) {
        return em.find(PolicyDetail.class, policyId);
    }
}
