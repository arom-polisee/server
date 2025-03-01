package com.arom_policy.service;

import com.arom_policy.domain.Policy;
import com.arom_policy.domain.PolicyDetail;
import com.arom_policy.dto.PolicyDetailResponseDto;
import com.arom_policy.dto.PolicyResponseDto;
import com.arom_policy.repository.PolicyRepository;
import com.arom_policy.repository.PolicyDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyDetailRepository policyDetailRepository;

    private static final String BASE_URL = "https://api.odcloud.kr/api";
    private static final String POLICY_API = "/gov24/v3/supportConditions";
    private static final String POLICY_DETAIL_API = "/gov24/v3/serviceList";
    private static final String API_KEY = "alJzQFG66OGwXQwLdSHNo0iEMEiyl+UF7XVBcEDLj17L2nGYdv6thwx1o5SxZA0R689p/1wSEzfjXF6V60d8vQ==";


    @Transactional
    public void fetchAndSavePolicies(int perPage) {
        int page = 1;
        boolean hasMore = true;

        // 모든 정책 리스트 가져오기
        while (hasMore) {
            hasMore = fetchPoliciesList(page, perPage);
            page++;
        }

        // 상세 정책 가져오기
        page = 1;
        hasMore = true;
        while (hasMore) {
            hasMore = fetchPoliciesDetail(page, perPage);
            page++;
        }
    }

    private boolean fetchPoliciesDetail(int page, int perPage) {
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

            PolicyDetailResponseDto response = parseJsonToPolicyDetailResponse(jsonResponse);
            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                log.info("✅ 상세 정책 데이터 없음, 종료.");
                return false;
            }

            response.getData().forEach(dto -> {
                Policy policy = policyRepository.findByPolicyId(dto.getServiceId());
                if (policy == null) return;

                PolicyDetail policyDetail = new PolicyDetail();
                policyDetail.setPolicy(policy);
                policyDetail.setId(dto.getServiceId());
                policyDetailRepository.save(policyDetail);
            });

            return true;

        } catch (Exception e) {
            log.error("❌ 상세 정책 API 요청 중 오류 발생", e);
            return false;
        }
    }

    private boolean fetchPoliciesList(int page, int perPage) {
        try {
            String encodedServiceKey = URLEncoder.encode(API_KEY, StandardCharsets.UTF_8.toString());

            String urlString = BASE_URL + POLICY_API
                    + "?page=" + page
                    + "&perPage=" + perPage
                    + "&returnType=json"
                    + "&serviceKey=" + encodedServiceKey;

            log.info("🚀 정책 목록 API 요청: {}", urlString);

            String jsonResponse = sendGetRequest(urlString);
            if (jsonResponse == null) return false;

            PolicyResponseDto response = parseJsonToPolicyResponse(jsonResponse);
            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                log.info("✅ 정책 데이터 없음, 종료.");
                return false;
            }

            List<Policy> policies = response.getData().stream()
                    .map(dto -> new Policy(dto.getId(), dto.getName()))
                    .toList();

            policyRepository.saveAll(policies);

            return true;

        } catch (Exception e) {
            log.error("❌ 정책 목록 API 요청 중 오류 발생", e);
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
            log.info("🔹 Response Code: {}", responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                log.error("❌ API 요청 실패. Response Code: {}", responseCode);
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

    private PolicyResponseDto parseJsonToPolicyResponse(String json) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(json, PolicyResponseDto.class);
        } catch (Exception e) {
            log.error("❌ JSON 파싱 오류 (PolicyResponseDto)", e);
            return null;
        }
    }

    private PolicyDetailResponseDto parseJsonToPolicyDetailResponse(String json) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(json, PolicyDetailResponseDto.class);
        } catch (Exception e) {
            log.error("❌ JSON 파싱 오류 (PolicyDetailResponseDto)", e);
            return null;
        }
    }
}