package com.arom.polisee.domain.api.service;


import com.arom.polisee.domain.policies.service.PolicyService;
import com.arom.polisee.domain.policy_requirements.service.PolicyRequirementsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor

public class ApiService {

    private final PolicyService policyService;
    private final PolicyRequirementsService policyRequirementsService;

    public void fetchPolicyRequirements(int perPage) {
        int page = 1;
        boolean hasMore = true;
        while (hasMore) {
            hasMore = policyRequirementsService.fetchPoliciesRequirements(page, perPage);
            page++;
        }
    }
    public void fetchPolicies(int perPage) {
        int page = 1;
        boolean hasMore = true;
        while (hasMore) {
            hasMore = policyService.fetchPolicies(page, perPage);
            page++;
        }
    }

}