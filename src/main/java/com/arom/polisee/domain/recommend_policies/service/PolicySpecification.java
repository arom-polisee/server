package com.arom.polisee.domain.recommend_policies.service;

import com.arom.polisee.domain.policies.entity.Policies;
import com.arom.polisee.domain.userInfo.UserInfo;
import org.springframework.data.jpa.domain.Specification;

public class PolicySpecification {

    // 성별
    public static Specification<Policies> matchGender(UserInfo user) {
        return (root, query, builder) -> {
            if (user.getGender() == UserInfo.Gender.MALE)        return builder.equal(root.get("male"), "Y");
            else if (user.getGender() == UserInfo.Gender.FEMALE) return builder.equal(root.get("female"), "Y");

            return builder.conjunction();
        };
    }

    // 나이
    public static Specification<Policies> matchAge(UserInfo user) {
        return (root, query, builder) -> builder.and(
                builder.lessThanOrEqualTo(root.get("startAge"), user.getAge()),
                builder.greaterThanOrEqualTo(root.get("endAge"), user.getAge())
        );
    }

    // 중위 소득
    public static Specification<Policies> matchMedianIncome(UserInfo user) {
        if (user.getMedianIncome() == null) {
            return (root, query, builder) -> builder.conjunction();
        }

        return (root, query, builder) -> {
            switch (user.getMedianIncome()) {
                case MEDIAN_INCOME_0_TO_50:     return builder.equal(root.get("medianIncome0To50"), "Y");
                case MEDIAN_INCOME_51_TO_75:    return builder.equal(root.get("medianIncome51To75"), "Y");
                case MEDIAN_INCOME_76_TO_100:   return builder.equal(root.get("medianIncome76To100"), "Y");
                case MEDIAN_INCOME_101_TO_200:  return builder.equal(root.get("medianIncome101To200"), "Y");
                case MEDIAN_INCOME_OVER_200:    return builder.equal(root.get("medianIncomeOver200"), "Y");
                default:                        return builder.disjunction();
            }
        };
    }

    // 자녀 유무
    public static Specification<Policies> matchHasChildren(UserInfo user) {
        if (user.getHasChildren() == null)
            return (root, query, builder) -> builder.conjunction();

        return (root, query, builder) -> {
            switch (user.getHasChildren()) {
                case EXPECTANT_COUPLE_OR_INFERTILITY:
                    return builder.equal(root.get("expectantCoupleOrInfertility"), "Y");
                case PREGNANT:
                    return builder.equal(root.get("pregnant"), "Y");
                case ADOPTION_OR_CHILDBIRTH:
                    return builder.equal(root.get("adoptionOrChildbirth"), "Y");
                default:
                    return builder.disjunction();
            }
        };
    }
}
