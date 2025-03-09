package com.arom.polisee.domain.policy_requirements;


import com.arom.polisee.domain.gender.Gender;
import com.arom.polisee.domain.policies.Policies;
import com.arom.polisee.domain.recommend_policy.RecommendPolicy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "policy_requirements")

public class PolicyRequirements {

    @Id
    @Column(name = "서비스 ID")
    private Long serviceId;

    // Policies와 1:1, PK 공유

    @OneToOne(mappedBy = "policyRequirements", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Policies policies;

    // 서비스명
    @Column(name = "서비스명")
    private String serviceName;

    // 성별
    @Enumerated(EnumType.STRING)
    @Column(name = "성별")
    private Gender gender;

    // 나이
    @Column(name = "나이")
    private Integer age;

    // user_residence(시)
    @Column(name = "user_residence(시)")
    private String userResidenceSi;

    // user_residence(구)
    @Column(name = "user_residence(구)")
    private String userResidenceGu;

    // 중위소득
    @Column(name = "중위소득")
    private String medianIncome;

    // 자녀유무
    @Column(name = "자녀유무")
    private String hasChildren;

    // 직업 및 학업 상태
    @Column(name = "직업 및 학업 상태")
    private String jobAndStudyStatus;

    // 근로상태여부
    @Column(name = "근로상태여부")
    private String employmentCondition;

    // 가족 형태
    @Column(name = "가족 형태")
    private String familyType;

    // 가구 형태
    @Column(name = "가구 형태")
    private String householdType;

    // 장애인/질병
    @Column(name = "장애인/질병")
    private String disabledOrDisease;

    // 복지대상자
    @Column(name = "복지대상자")
    private String welfareTarget;
}
