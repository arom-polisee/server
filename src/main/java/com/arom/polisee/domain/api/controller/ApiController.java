package com.arom.polisee.domain.api.controller;


import com.arom.polisee.domain.api.service.ApiService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // 🔹 모든 도메인에서 접근 허용
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    // 정책 리스트 저장
    @GetMapping("/policies/fetch")
    public String fetchPolicies() {
        apiService.fetchPoliciesDetails(500);
        apiService.fetchPolicies(500);
        return "정책 리스트 저장 완료!";
    }

}