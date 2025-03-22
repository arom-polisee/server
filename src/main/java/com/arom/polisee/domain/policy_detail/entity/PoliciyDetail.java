package com.arom.polisee.domain.policy_detail.entity;

import com.arom.polisee.domain.policy_detail.dto.PoliciyDetailDTO;
import com.arom.polisee.domain.policies.entity.Policies;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PoliciyDetail {
    @Id
    @Column(name = "service_id")
    private String id;  // PK

    @OneToOne
    @MapsId
    @JoinColumn(name = "service_id")
    private Policies policies;

    @Column(name = "register_date")
    private String registerDate;   // 등록 일시

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "detail_url")
    private String detailUrl;

    @Column(name = "service_purpose_summary")
    private String servicePurposeSummary;

    @Column(name = "service_field") // 서비스분야
    private String serviceField;

    @Column(name = "selection_criteria", columnDefinition = "TEXT") // 선정기준
    private String selectionCriteria;

    @Column(name = "agency_name") // 소관기관명
    private String agencyName;

    @Column(name = "agency_type") // 소관기관유형
    private String agencyType;

    @Column(name = "agency_code") // 소관기관코드
    private String agencyCode;

    @Column(name = "updated_datetime") // 수정일시
    private String updatedDatetime; // 수정 일시 (문자열 예시)

    @Column(name = "application_deadline") // 신청기한
    private String applicationDeadline;

    @Column(name = "application_method", columnDefinition = "TEXT") // 신청방법
    private String applicationMethod;

    @Column(name = "contact_number",columnDefinition = "TEXT") // 전화문의
    private String contactNumber;

    @Column(name = "reception_agency") // 접수기관
    private String receptionAgency;

    @Column(name = "view_count") // 조회수
    private Integer viewCount; // 정수형 조회수

    @Column(name = "support_contents", columnDefinition = "TEXT") // 지원내용
    private String supportContents;

    @Column(name = "support_target", columnDefinition = "TEXT") // 지원대상
    private String supportTarget;

    @Column(name = "support_type") // 지원유형
    private String supportType;

    public PoliciyDetail fromDto(PoliciyDetailDTO dto) {
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