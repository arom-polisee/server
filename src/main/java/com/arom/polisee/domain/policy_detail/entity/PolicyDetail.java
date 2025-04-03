package com.arom.polisee.domain.policy_detail.entity;

import com.arom.polisee.domain.policy_detail.dto.PoliciyDetailDTO;
import com.arom.polisee.domain.policies.entity.Policies;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PolicyDetail {
    @Id
    private String id;  // PK

    @OneToOne
    @MapsId
    @JoinColumn(name = "policies_id")
    private Policies policies;

    private String registerDate;   // 등록 일시

    private String departmentName;

    private String detailUrl;

    private String servicePurposeSummary;

    // 서비스분야
    private String serviceField;

    @Column(columnDefinition = "TEXT") // 선정기준
    private String selectionCriteria;

    // 소관기관명
    private String agencyName;

    // 소관기관유형
    private String agencyType;

    // 소관기관코드
    private String agencyCode;

    // 수정일시
    private String updatedDatetime;

    // 신청기한
    private String applicationDeadline;

    @Column(columnDefinition = "TEXT") // 신청방법
    private String applicationMethod;

    @Column(columnDefinition = "TEXT") // 전화문의
    private String contactNumber;

    // 접수기관
    private String receptionAgency;

    // 조회수
    private Integer viewCount; // 정수형 조회수

    @Column(columnDefinition = "TEXT") // 지원내용
    private String supportContents;

    @Column(columnDefinition = "TEXT") // 지원대상
    private String supportTarget;

    // 지원유형
    private String supportType;

    public PolicyDetail fromDto(PoliciyDetailDTO dto) {
        this.setRegisterDate(dto.getRegisterDate());
        this.setDepartmentName(dto.getDepartmentName());
        this.setDetailUrl(dto.getDetailUrl());
        this.setServicePurposeSummary(dto.getServicePurposeSummary());
        this.setServiceField(dto.getServiceField());
        this.setSelectionCriteria(dto.getSelectionCriteria());
        this.setAgencyName(dto.getAgencyName());
        this.setAgencyType(dto.getAgencyType());
        this.setAgencyCode(dto.getAgencyCode());
        this.setUpdatedDatetime(dto.getUpdatedDatetime());
        this.setApplicationDeadline(dto.getApplicationDeadline());
        this.setApplicationMethod(dto.getApplicationMethod());
        this.setContactNumber(dto.getContactNumber());
        this.setReceptionAgency(dto.getReceptionAgency());
        this.setViewCount(dto.getViewCount());
        this.setSupportContents(dto.getSupportContents());
        this.setSupportTarget(dto.getSupportTarget());
        this.setSupportType(dto.getSupportType());
        return this;
    }
}