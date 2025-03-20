package com.arom.polisee.domain.policies.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoliciesDTO {

    @JsonProperty("서비스ID")
    private String id;  // PK

    @JsonProperty("서비스명")
    private String name;

    @JsonProperty("등록일시")
    private String registerDate;   // 등록 일시

    @JsonProperty("부서명")
    private String departmentName;

    @JsonProperty("상세조회URL")
    private String detailUrl;

    @JsonProperty("서비스목적요약")
    private String servicePurposeSummary;

    @JsonProperty("서비스분야")
    private String serviceField;

    @JsonProperty("선정기준")
    private String selectionCriteria;

    @JsonProperty("소관기관명")
    private String agencyName;

    @JsonProperty("소관기관유형")
    private String agencyType;

    @JsonProperty("소관기관코드")
    private String agencyCode;

    @JsonProperty("수정일시")
    private String updatedDatetime; // 수정 일시 (문자열 예시)

    @JsonProperty("신청기한")
    private String applicationDeadline;

    @JsonProperty("신청방법")
    private String applicationMethod;

    @JsonProperty("전화문의")
    private String contactNumber;

    @JsonProperty("접수기관")
    private String receptionAgency;

    @JsonProperty("조회수")
    private Integer viewCount; // 정수형 조회수

    @JsonProperty("지원내용")
    private String supportContents;

    @JsonProperty("지원대상")
    private String supportTarget;

    @JsonProperty("지원유형")
    private String supportType;

    @JsonProperty("사용자구분")
    private String remarks;
}