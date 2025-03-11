package com.arom.polisee.domain.api.service;


import com.arom.polisee.domain.api.dto.PoliciesRequirementsResponseDto;
import com.arom.polisee.domain.api.dto.PoliciesResponseDto;
import com.arom.polisee.domain.policy_requirements.entity.PolicyRequirements;
import com.arom.polisee.domain.policy_requirements.repository.PolicyRequirementsRepository;
import com.arom.polisee.domain.policies.repository.PoliciesRepository;
import com.arom.polisee.domain.policies.entity.Policies;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {

    private final PoliciesRepository policyRepository;
    private final PolicyRequirementsRepository policyRequirementsRepository;

    private static final String BASE_URL = "https://api.odcloud.kr/api";
    private static final String POLICY_DETAIL_API = "/gov24/v3/supportConditions";
    private static final String POLICY_API = "/gov24/v3/serviceList";

    @Value("${polisee.api.key}")
    private String API_KEY;

    @Transactional
    public void fetchAndSavePolicies(int perPage) {

        // 상세 정책 가져오기
        int page = 1;

        boolean hasMore = true;
        while (hasMore) {
            hasMore = fetchPoliciesRequirements(page, perPage);
            page++;
        }
//         모든 정책 리스트 가져오기
        page = 1;
        hasMore = true;
        while (hasMore) {
            hasMore = fetchPolicies(page, perPage);
            page++;
        }
    }

    private boolean fetchPolicies(int page, int perPage) {
        try {
            String encodedServiceKey = URLEncoder.encode(API_KEY, StandardCharsets.UTF_8.toString());

            String urlString = BASE_URL + POLICY_API
                    + "?page=" + page
                    + "&perPage=" + perPage
                    + "&returnType=json"
                    + "&serviceKey=" + encodedServiceKey;

            log.info("🚀 정책 목록 API page: {}", page);

            String jsonResponse = sendGetRequest(urlString);
            if (jsonResponse == null) return false;
            PoliciesResponseDto response = parseJsonToPolicyResponse(jsonResponse);

            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                log.info("✅정책 데이터 읽기 종료");
                return false;
            }

            List<Policies> policiesList = response.getData().stream()
                    .map(dto -> {
                        Policies policies = new Policies();
                        PolicyRequirements policyRequirements = policyRequirementsRepository.findById(dto.getId());
                        policies.setId(dto.getId());
                        policies.fromDto(dto);
                        policies.setPolicyRequirements(policyRequirements);
                        policyRequirements.setPolicies(policies);
                        return policies;
                    }).toList();

            policyRepository.saveAll(policiesList);
            return true;

        } catch (Exception e) {
            log.error("❌정책 목록 API 요청 중 오류 발생", e);
            return false;
        }
    }

    private boolean fetchPoliciesRequirements(int page, int perPage) {
        try {
            String encodedServiceKey = URLEncoder.encode(API_KEY, StandardCharsets.UTF_8.toString());

            String urlString = BASE_URL + POLICY_DETAIL_API
                    + "?page=" + page
                    + "&perPage=" + perPage
                    + "&returnType=json"
                    + "&serviceKey=" + encodedServiceKey;

            log.info("📌 상세 정책 API 요청: {}", urlString);

            String jsonResponse = sendGetRequest(urlString);
            if (jsonResponse == null) return false;

            PoliciesRequirementsResponseDto response = parseJsonToPolicyDetailResponse(jsonResponse);
            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                log.info("✅ 상세 정책 데이터 읽기 종료.");
                return false;
            }

            List<PolicyRequirements> policyRequirementsList = response.getData().stream()
                    .map(dto -> {
                        PolicyRequirements policyRequirements = new PolicyRequirements();
                        policyRequirements.setServiceId(dto.getId());
                        policyRequirements.fromDto(dto);
                        return policyRequirements;
                    }).toList();
            policyRequirementsRepository.saveAll(policyRequirementsList);
            return true;

        } catch (Exception e) {
            log.error("❌ 상세 정책 API 요청 중 오류 발생", e);
            return false;
        }
    }



    private String sendGetRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                log.error("❌ API 요청 실패 Response Code: {}", responseCode);
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();

        } catch (Exception e) {
            log.error("❌ API 요청 중 오류 발생", e);
            return null;
        }
    }

    private PoliciesResponseDto parseJsonToPolicyResponse(String json) {
        try {
            return new ObjectMapper().readValue(json, PoliciesResponseDto.class);
        } catch (Exception e) {
            log.error("❌ JSON 파싱 오류 (PolicyResponseDto)", e);
            return null;
        }
    }

    private PoliciesRequirementsResponseDto parseJsonToPolicyDetailResponse(String json) {
        try {
            return new ObjectMapper().readValue(json, PoliciesRequirementsResponseDto.class);
        } catch (Exception e) {
            log.error("❌ JSON 파싱 오류 (PolicyDetailResponseDto)", e);
            return null;
        }
    }
}