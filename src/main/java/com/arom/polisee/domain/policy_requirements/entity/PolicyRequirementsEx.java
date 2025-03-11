//package com.arom.polisee.domain.policy_requirements.entity;
//
//
//import com.arom.polisee.domain.gender.Gender;
//import com.arom.polisee.domain.policies.entity.Policies;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "policy_requirements")
//
//public class PolicyRequirementsEx {
//
//    @Id
//    @Column(name = "service_id")
//    private String serviceId;
//
//    // Policies와 1:1, PK 공유
//
//    @OneToOne(mappedBy = "policyRequirements", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
//    private Policies policies;
//
//    // 서비스명
//    @Column(name = "service_name")
//    private String serviceName;
//
//    // 성별
//    @Enumerated(EnumType.STRING)
//    @Column(name = "gender")
//    private Gender gender;
//
//    // 나이
//    @Column(name = "start_age")
//    private Integer startAge;
//
//    @Column(name = "end_age")
//    private Integer endAge;
//
//    // user_residence(주소)
//    @Column(name = "user_residence(시)")
//    private String userResidenceSi;
//
//    // user_residence(구)
//    @Column(name = "user_residence(구)")
//    private String userResidenceGu;
//
//    // 중위소득 범위
//    @Column(name = "max_median_income_percentage")
//    private Integer maxMedianIncomePercentage;
//
//    // 자녀유무
//    @Column(name = "has_children")
//    private String hasChildren;
//
//    // 직업 및 학업 상태
//    @Column(name = "job_and_study_status")
//    private String jobAndStudyStatus;
//
//    // 근로상태여부
//    @Column(name = "employment_condition")
//    private String employmentCondition;
//
//    // 가족 형태
//    @Column(name = "family_type")
//    private String familyType;
//
//    // 가구 형태
//    @Column(name = "household_type")
//    private String householdType;
//
//    // 장애 여부
//    @Column(name = "disabled")
//    private String disabled;
//
//    // 복지대상자
//    @Column(name = "welfare_target")
//    private String welfareTarget;
//}
