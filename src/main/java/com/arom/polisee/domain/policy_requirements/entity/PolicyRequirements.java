package com.arom.polisee.domain.policy_requirements.entity;


import com.arom.polisee.domain.policies.dto.PoliciesDTO;
import com.arom.polisee.domain.policies.entity.Policies;
import com.arom.polisee.domain.policy_requirements.dto.PolicyRequirementsDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "policy_requirements")

public class PolicyRequirements {

    @Id
    @Column(name = "service_id")
    private String serviceId;

    // Policies와 1:1, PK 공유

    @OneToOne(mappedBy = "policyRequirements", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Policies policies;

    // 서비스명
    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "male")
    private String male;

    @Column(name = "female")
    private String female;

    @Column(name = "start_age")
    private Integer startAge;

    @Column(name = "end_age")
    private Integer endAge;

    @Column(name = "median_income_0~50%")
    private String medianIncome0To50;

    @Column(name = "median_income_51~75%")
    private String medianIncome51To75;

    @Column(name = "median_income_75~100%")
    private String medianIncome76To100;

    @Column(name = "median_income_101~200%")
    private String medianIncome101To200;

    @Column(name = "median_income_over_200%")
    private String medianIncomeOver200;

    // 예비부부/난임
    @Column(name = "expectant_couple_or_infertility")
    private String expectantCoupleOrInfertility;

    // 임산부
    @Column(name = "pregnant")
    private String pregnant;

    // 출산/입양
    @Column(name = "adoption_or_childbirth")
    private String adoptionOrChildbirth;

    // 농업인
    @Column(name = "farmer")
    private String farmer;

    // 어업인
    @Column(name = "fishman")
    private String fishman;

    // 축산업인
    @Column(name = "livestock_farmer")
    private String LivestockFarmer;

    // 임업인
    @Column(name = "forest_worker")
    private String forestWorker;

    // 초등학생
    @Column(name = "elementary")
    private String elementary;

    // 중학생
    @Column(name = "mid_school")
    private String midSchool;

    //고등학생
    @Column(name = "high_school")
    private String highSchool;

    // 대학생/대학원생
    @Column(name = "University")
    private String university;

    // 근로자/직장인
    @Column(name = "employee")
    private String employee;

    // 구직자/실업자
    @Column(name = "unemployed")
    private String unemployed;

    // 장애인
    @Column(name = "disabled")
    private String disabled;

    // 국가보훈대상자
    @Column(name = "national_merit")
    private String nationalMerit;

    // 질병/질환자
    @Column(name = "sick")
    private String sick;

    // 해당사항 없음 (예비부부 부터 대학생까지)
    @Column(name = "personality_nothing")
    private String personalityNothing;

    // 다문화가정
    @Column(name = "multicultural")
    private String multicultural;

    // 북한이탈주민
    @Column(name = "defector")
    private String defector;

    // 한부모가정
    @Column(name = "single_parent")
    private String singleParent;

    // 1인가구
    @Column(name = "solo")
    private String solo;

    // 다자녀가구
    @Column(name = "many_children")
    private String manyChildren;

    // 무주택세대
    @Column(name = "homeless")
    private String homeless;

    // 신규전입
    @Column(name = "new_home")
    private String newHome;

    // 확대가족
    @Column(name = "extended_family")
    private String extendedFamily;

    // 해당사항없음 (다문화가정부터 확대가족까지)
    @Column(name = "family_nothing")
    private String familyNothing;

    public PolicyRequirements fromDto(PolicyRequirementsDTO dto) {
        setServiceName(dto.getName());
        setMale(dto.getJA0101());
        setFemale(dto.getJA0102());
        setStartAge(dto.getJA0110());
        setEndAge(dto.getJA0111());
        setMedianIncome0To50(dto.getJA0201());
        setMedianIncome51To75(dto.getJA0202());
        setMedianIncome76To100(dto.getJA0203());
        setMedianIncome101To200(dto.getJA0204());
        setMedianIncomeOver200(dto.getJA0205());
        setExpectantCoupleOrInfertility(dto.getJA0301());
        setPregnant(dto.getJA0302());
        setAdoptionOrChildbirth(dto.getJA0303());
        setFarmer(dto.getJA0313());
        setFishman(dto.getJA0314());
        setLivestockFarmer(dto.getJA0315());
        setForestWorker(dto.getJA0316());
        setElementary(dto.getJA0317());
        setMidSchool(dto.getJA0318());
        setHighSchool(dto.getJA0319());
        setUniversity(dto.getJA0320());
        setPersonalityNothing(dto.getJA0322());
        setEmployee(dto.getJA0326());
        setUnemployed(dto.getJA0327());
        setDisabled(dto.getJA0328());
        setNationalMerit(dto.getJA0329());
        setSick(dto.getJA0330());
        setMulticultural(dto.getJA0401());
        setDefector(dto.getJA0402());
        setSingleParent(dto.getJA0403());
        setSolo(dto.getJA0404());
        setFamilyNothing(dto.getJA0410());
        return this;
    }
}
