package com.arom.polisee.domain.policies.repository;

import com.arom.polisee.domain.policies.entity.Policies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class PoliciesRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Policies policies) {
        em.persist(policies);
    }

    public void saveAll(List<Policies> policies) {
        for (Policies newPolicies : policies) {
            save(newPolicies);
        }
    }
    public Optional<Policies> findById(String id) {
        return Optional.ofNullable(em.find(Policies.class, id));
    }
}
