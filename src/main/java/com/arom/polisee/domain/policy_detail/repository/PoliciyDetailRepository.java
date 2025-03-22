package com.arom.polisee.domain.policy_detail.repository;

import com.arom.polisee.domain.policy_detail.entity.PoliciyDetail;
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

    public void save(PoliciyDetail policy) {
        em.persist(policy);
    }

    public void saveAll(List<PoliciyDetail> policies) {
        for (PoliciyDetail policy : policies) save(policy);
    }

    public PoliciyDetail findByPolicyId(String policyId) {
        return em.find(PoliciyDetail.class, policyId);
    }
}
