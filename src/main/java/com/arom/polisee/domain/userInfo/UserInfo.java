package com.arom.polisee.domain.userInfo;


import com.arom.polisee.domain.user.User;
import com.arom.polisee.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserInfo extends BaseEntity {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING) private Gender gender;

    private Integer age;

    private String userResidenceSi;

    private String userResidenceGu;

    //중위소득
    @Enumerated(EnumType.STRING) private MedianIncome medianIncome;

    //자녀유무
    @Enumerated(EnumType.STRING) private HasChildren hasChildren;

    //직종분류
    @Enumerated(EnumType.STRING) private OccupationType occupationType;

    //직업 및 학업 상태
    @NonNull @Enumerated(EnumType.STRING) private JobOrStudyStatus jobOrStudyStatus;

    //근로상태여부
    //private String employmentCondition;

    //가족 형태
    @Enumerated(EnumType.STRING) private FamilyType familyType;

    //가구 형태
    @Enumerated(EnumType.STRING) private HouseholdType householdType;

    //장애인/질병
    private String disabledOrDisease;

    //복지대상자
    private String welfareTarget;

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum MedianIncome {
        MEDIAN_INCOME_0_TO_50,
        MEDIAN_INCOME_51_TO_75,
        MEDIAN_INCOME_76_TO_100,
        MEDIAN_INCOME_101_TO_200,
        MEDIAN_INCOME_OVER_200
    }

    public enum HasChildren {
        EXPECTANT_COUPLE_OR_INFERTILITY,
        PREGNANT,
        ADOPTION_OR_CHILDBIRTH
    }

    public enum OccupationType {
        FARMER,              // 농업인
        FISHERMAN,           // 어업인
        LIVESTOCK_FARMER,    // 축산업인
        FOREST_WORKER        // 임업인
    }

    public enum JobOrStudyStatus {
        ELEMENTARY,
        MIDSCHOOL,
        HIGHSCHOOL,
        UNIVERSITY,
        EMPLOYEE,
        UNEMPLOYED
    }

    public enum FamilyType {
        PERSONALITY_NOTHING,   // 해당사항 없음
        MULTICULTURAL,         // 다문화가정
        DEFECTOR,              // 북한이탈주민
        SINGLE_PARENT          // 한부모가정
    }

    public enum HouseholdType {
        SOLO,              // 1인가구
        MANY_CHILDREN,     // 다자녀가구
        HOMELESS,          // 무주택세대
        NEW_HOME,          // 신규전입
        EXTENDED_FAMILY,   // 확대가족
        FAMILY_NOTHING     // 해당사항 없음 (다문화가정부터 확대가족까지)
    }

}
