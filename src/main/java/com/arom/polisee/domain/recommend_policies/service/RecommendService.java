package com.arom.polisee.domain.recommend_policies.service;

import com.arom.polisee.domain.policies.entity.Policies;
import com.arom.polisee.domain.policies.repository.EligiblePolicesRepository;
import com.arom.polisee.domain.recommend_policies.RecommendPolicies;
import com.arom.polisee.domain.recommend_policies.repository.RecommendPoliciesRepository;
import com.arom.polisee.domain.userInfo.UserInfo;
import com.arom.polisee.domain.userInfo.repository.UserInfoRepository;
import com.arom.polisee.global.exception.BaseException;
import com.arom.polisee.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendService {

    private final EligiblePolicesRepository eligiblePolicesRepository;
    private final UserInfoRepository userInfoRepository;
    private final RecommendPoliciesRepository recommendPoliciesRepository;

    public List<String> matchEligiblePolicies(Long userId) {
        UserInfo userInfo = userInfoRepository.findById(userId)
                .orElseThrow(() -> BaseException.from(ErrorCode.USER_NOT_FOUND));

        // DB에서 1차 필터링된 정책들만 조회
        Specification<Policies> spec = Specification.where(PolicySpecification.matchGender(userInfo))
                .and(PolicySpecification.matchAge(userInfo))
                .and(PolicySpecification.matchMedianIncome(userInfo))
                .and(PolicySpecification.matchHasChildren(userInfo));

        List<Policies> policiesList = eligiblePolicesRepository.findAll(spec);

        List<String> eligiblePolicyIds = new ArrayList<>();

        for (Policies policy : policiesList) {
            if (isCompletelyEligible(userInfo, policy)) {
                registerRecommendPolicy(userInfo, policy);
                eligiblePolicyIds.add(policy.getId());
            }
        }
        return eligiblePolicyIds;
    }

    public void registerRecommendPolicy(UserInfo userInfo, Policies policy) {
        RecommendPolicies recommendPolicy = new RecommendPolicies();
        recommendPolicy.setUser(userInfo.getUser());
        recommendPolicy.setPolicies(policy);
        recommendPoliciesRepository.save(recommendPolicy);
    }

    public boolean isCompletelyEligible(UserInfo userInfo, Policies policy) {
        if (userInfo.getOccupationType() != null && !isOccupationEligible(userInfo, policy)) return false;
        if (userInfo.getFamilyType() != null && !isFamilyTypeEligible(userInfo, policy)) return false;
        if (userInfo.getHouseholdType() != null && !isHouseholdTypeEligible(userInfo, policy)) return false;
        if (!isJobOrStudyStatusEligible(userInfo, policy)) return false;
        return true;
    }

    private boolean isEligible(String policyField) {
        return "Y".equals(policyField);
    }

    // 직종 분류 체크
    private boolean isOccupationEligible(UserInfo user, Policies policy) {
        switch (user.getOccupationType()) {
            case FARMER:            return isEligible(policy.getFarmer());
            case FISHERMAN:         return isEligible(policy.getFisherman());
            case LIVESTOCK_FARMER:  return isEligible(policy.getLivestockFarmer());
            case FOREST_WORKER:     return isEligible(policy.getForestWorker());
            default:                return false;
        }
    }

    // 가족 형태 체크
    private boolean isFamilyTypeEligible(UserInfo user, Policies policy) {
        switch (user.getFamilyType()) {
            case PERSONALITY_NOTHING:   return isEligible(policy.getPersonalityNothing());
            case MULTICULTURAL:         return isEligible(policy.getMulticultural());
            case DEFECTOR:              return isEligible(policy.getDefector());
            case SINGLE_PARENT:         return isEligible(policy.getSingleParent());
            default:                    return false;
        }
    }

    // 가구 형태 체크
    private boolean isHouseholdTypeEligible(UserInfo user, Policies policy) {
        switch (user.getHouseholdType()) {
            case SOLO:              return isEligible(policy.getSolo());
            case MANY_CHILDREN:     return isEligible(policy.getManyChildren());
            case HOMELESS:          return isEligible(policy.getHomeless());
            case NEW_HOME:          return isEligible(policy.getNewHome());
            case EXTENDED_FAMILY:   return isEligible(policy.getExtendedFamily());
            case FAMILY_NOTHING:    return isEligible(policy.getFamilyNothing());
            default:                return false;
        }
    }

    // 직업/학업 상태 체크
    private boolean isJobOrStudyStatusEligible(UserInfo user, Policies policy) {
        switch (user.getJobOrStudyStatus()) {
            case ELEMENTARY:    return isEligible(policy.getElementary());
            case MIDSCHOOL:     return isEligible(policy.getMidSchool());
            case HIGHSCHOOL:    return isEligible(policy.getHighSchool());
            case UNIVERSITY:    return isEligible(policy.getUniversity());
            case EMPLOYEE:      return isEligible(policy.getEmployee());
            case UNEMPLOYED:    return isEligible(policy.getUnemployed());
            default:            return false;
        }
    }
}
