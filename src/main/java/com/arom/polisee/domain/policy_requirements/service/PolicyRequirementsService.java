package com.arom.polisee.domain.policy_requirements.service;

import com.arom.polisee.domain.api.dto.PoliciesRequirementsResponseDto;
import com.arom.polisee.domain.policy_requirements.entity.PolicyRequirements;
import com.arom.polisee.domain.policy_requirements.repository.PolicyRequirementsRepository;
import com.arom.polisee.global.exception.BaseException;
import com.arom.polisee.global.exception.error.ErrorCode;
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
@Transactional(readOnly = true)
public class PolicyRequirementsService {
    private final PolicyRequirementsRepository policyRequirementsRepository;

    private static final String BASE_URL = "https://api.odcloud.kr/api";
    private static final String POLICY_DETAIL_API = "/gov24/v3/supportConditions";
    private final ObjectMapper objectMapper;
    @Value("${polisee.api.key}")
    private String API_KEY;

    @Transactional
    public boolean fetchPoliciesRequirements(int page, int perPage) {
        try {
            String encodedServiceKey = URLEncoder.encode(API_KEY, StandardCharsets.UTF_8.toString());

            String urlString = BASE_URL + POLICY_DETAIL_API
                    + "?page=" + page
                    + "&perPage=" + perPage
                    + "&returnType=json"
                    + "&serviceKey=" + encodedServiceKey;

            log.info("📌 상세 정책 API 요청: {}", page);

            String jsonResponse = sendGetRequest(urlString);
            if (jsonResponse == null) return false;

            PoliciesRequirementsResponseDto response = parseJsonToPolicyDetailResponse(jsonResponse);

            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                log.info("더 이상 받아올 정책이 없습니다");
                return false;
            }

            List<PolicyRequirements> policyRequirementsList = response.getData().stream()
                    .map(dto -> {
                        PolicyRequirements policyRequirements = new PolicyRequirements();
                        policyRequirements.setId(dto.getId());
                        policyRequirements.fromDto(dto);
                        return policyRequirements;
                    }).toList();
            policyRequirementsRepository.saveAll(policyRequirementsList);
            return true;

        } catch (Exception e) {
            throw BaseException.from(ErrorCode.POLICY_DETAIL_API_ERROR,e.getMessage());
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
                throw BaseException.from(ErrorCode.INTERNAL_SERVER_ERROR);
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
            throw BaseException.from(ErrorCode.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    private PoliciesRequirementsResponseDto parseJsonToPolicyDetailResponse(String json) {
        try {
            return objectMapper.readValue(json, PoliciesRequirementsResponseDto.class);
        } catch (Exception e) {
            throw BaseException.from(ErrorCode.JSON_PARSING_ERROR,e.getMessage());
        }
    }
}
