package com.arom.polisee.domain.recommend_policies.repository;

import com.arom.polisee.domain.policies.entity.Policies;
import com.arom.polisee.domain.recommend_policies.RecommendPolicies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendPoliciesRepository extends JpaRepository<RecommendPolicies, Long>{

}
