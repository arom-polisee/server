package com.arom.polisee.domain.recommend_policies.controller;

import com.arom.polisee.domain.recommend_policies.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/{userId}")
    public List<String> recommendPolicies(@PathVariable Long userId) {
        return recommendService.matchEligiblePolicies(userId);
    }
}

