package com.arom.polisee.domain.policies;

import com.arom.polisee.domain.policy_requirements.PolicyRequirements;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "policies")
public class Policies {
    @Id
    @Column(name = "서비스 ID")
    private Long serviceId;  // PK

    @OneToOne
    @MapsId
    @JoinColumn(name = "서비스 ID")
    private PolicyRequirements policyRequirements;


    @Column(name = "등록일시")
    private String registerDate;   // 등록 일시

    @Column(name = "부서명")
    private String departmentName;

    @Column(name = "상세조회URL")
    private String detailUrl;

    @Column(name = "서비스목적요약")
    private String servicePurposeSummary;

    @Column(name = "서비스분야")
    private String serviceField;

    @Column(name = "선정기준")
    private String selectionCriteria;

    @Column(name = "소관기관명")
    private String agencyName;

    @Column(name = "소관기관유형")
    private String agencyType;

    @Column(name = "소관기관코드")
    private String agencyCode;

    @Column(name = "수정일시")
    private String updatedDatetime; // 수정 일시 (문자열 예시)

    @Column(name = "신청기한")
    private String applicationDeadline;

    @Column(name = "신청방법")
    private String applicationMethod;

    @Column(name = "전화문의")
    private String contactNumber;

    @Column(name = "접수기관")
    private String receptionAgency;

    @Column(name = "조회수")
    private Integer viewCount; // 정수형 조회수

    @Column(name = "지원내용")
    private String supportContents;

    @Column(name = "지원대상")
    private String supportTarget;

    @Column(name = "지원유형")
    private String supportType;



}