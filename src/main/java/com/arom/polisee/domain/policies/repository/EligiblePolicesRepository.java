package com.arom.polisee.domain.policies.repository;

import com.arom.polisee.domain.policies.entity.Policies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EligiblePolicesRepository extends JpaRepository<Policies, Long>,
        JpaSpecificationExecutor<Policies> {
}
