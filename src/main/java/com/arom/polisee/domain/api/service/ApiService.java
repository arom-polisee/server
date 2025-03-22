package com.arom.polisee.domain.api.service;


import com.arom.polisee.domain.policy_detail.service.PoliciyDetailService;
import com.arom.polisee.domain.policies.service.PoliciesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor

public class ApiService {

    private final PoliciyDetailService policiyDetailService;
    private final PoliciesService policiesService;

    public void fetchPolicyRequirements(int perPage) {
        int page = 1;
        boolean hasMore = true;
        while (hasMore) {
            hasMore = policiesService.fetchPoliciesRequirements(page, perPage);
            page++;
        }
    }
    public void fetchPolicies(int perPage) {
        int page = 1;
        boolean hasMore = true;
        while (hasMore) {
            hasMore = policiyDetailService.fetchPolicies(page, perPage);
            page++;
        }
    }

}