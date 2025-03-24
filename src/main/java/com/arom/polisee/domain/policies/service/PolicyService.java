package com.arom.polisee.domain.policies.service;

import com.arom.polisee.domain.api.dto.PoliciesResponseDto;
import com.arom.polisee.domain.policies.entity.Policies;
import com.arom.polisee.domain.policies.repository.PoliciesRepository;
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
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {
    private final PolicyRequirementsRepository policyRequirementsRepository;
    private final PoliciesRepository policiesRepository;

    private static final String BASE_URL = "https://api.odcloud.kr/api";
    private static final String POLICY_API = "/gov24/v3/serviceList";
    private final ObjectMapper objectMapper;
    @Value("${polisee.api.key}")
    private String API_KEY;

    @Transactional
    public boolean fetchPolicies(int page, int perPage) {
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
                        Optional<PolicyRequirements> policyRequirements = policyRequirementsRepository.findById(dto.getId());

                        // policyRequirements가 존재하지 않으면 null 반환
                        if (policyRequirements.isEmpty()) {
                            log.info("{}에 해당하는 PolicyRequirements가 없습니다!",dto.getId());
                            return null;
                        }

                        Policies policies = new Policies();
                        policies.setId(policyRequirements.get().getId());
                        policies.fromDto(dto);
                        policies.setPolicyRequirements(policyRequirements.get());
                        return policies;
                    })
                    .filter(Objects::nonNull) // null 값 제거
                    .toList();

            policiesRepository.saveAll(policiesList);
            return true;

        } catch (Exception e) {
            throw BaseException.from(ErrorCode.POLICY_API_ERROR,e.getMessage());
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
    private PoliciesResponseDto parseJsonToPolicyResponse(String json) {
        try {
            return objectMapper.readValue(json, PoliciesResponseDto.class);
        } catch (Exception e) {
            throw BaseException.from(ErrorCode.JSON_PARSING_ERROR,e.getMessage());
        }
    }
}
