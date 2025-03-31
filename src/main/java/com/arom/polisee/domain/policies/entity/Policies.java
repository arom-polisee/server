package com.arom.polisee.domain.policies.entity;


import com.arom.polisee.domain.policy_detail.entity.PolicyDetail;
import com.arom.polisee.domain.policies.dto.PoliciesDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Policies {

    @Id
    private String id;

    // Policies와 1:1, PK 공유

    @OneToOne(mappedBy = "policies", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private PolicyDetail policyDetail;

    // 서비스명
    private String serviceName;

    private String male;

    private String female;

    private Integer startAge;

    private Integer endAge;

    private String medianIncome0To50;

    private String medianIncome51To75;

    private String medianIncome76To100;

    private String medianIncome101To200;

    private String medianIncomeOver200;

    // 예비부부/난임
    private String expectantCoupleOrInfertility;

    // 임산부
    private String pregnant;

    // 출산/입양
    private String adoptionOrChildbirth;

    // 농업인
    private String farmer;

    // 어업인
    private String fisherman;

    // 축산업인
    private String livestockFarmer;

    // 임업인
    private String forestWorker;

    // 초등학생
    private String elementary;

    // 중학생
    private String midSchool;

    //고등학생
    private String highSchool;

    // 대학생/대학원생
    private String university;

    // 근로자/직장인
    private String employee;

    // 구직자/실업자
    private String unemployed;

    // 장애인
    private String disabled;

    // 국가보훈대상자
    private String nationalMerit;

    // 질병/질환자
    private String sick;

    // 해당사항 없음 (예비부부 부터 대학생까지)
    private String personalityNothing;

    // 다문화가정
    private String multicultural;

    // 북한이탈주민
    private String defector;

    // 한부모가정
    private String singleParent;

    // 1인가구
    private String solo;

    // 다자녀가구
    private String manyChildren;

    // 무주택세대
    private String homeless;

    // 신규전입
    private String newHome;

    // 확대가족
    private String extendedFamily;

    // 해당사항없음 (다문화가정부터 확대가족까지)
    private String familyNothing;

    public Policies fromDto(PoliciesDTO dto) {
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
        setFisherman(dto.getJA0314());
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
