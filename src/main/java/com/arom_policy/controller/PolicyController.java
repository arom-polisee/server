package com.arom_policy.controller;

import com.arom_policy.service.PolicyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // 🔹 모든 도메인에서 접근 허용
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // 정책 리스트 저장
    @GetMapping("/policies/fetch")
    public String fetchPolicies() {
        policyService.fetchAndSavePolicies(100);
        return "정책 리스트 저장 완료!";
    }

}