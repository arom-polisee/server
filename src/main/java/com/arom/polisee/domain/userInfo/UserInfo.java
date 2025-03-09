package com.arom.polisee.domain.userInfo;


import com.arom.polisee.domain.gender.Gender;
import com.arom.polisee.domain.user.User;
import com.arom.polisee.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "성별", length = 10)
    private Gender gender;

    @Column(name = "나이")
    private Integer age;

    @Column(name = "user_residence(시)")
    private String userResidenceSi;

    @Column(name = "user_residence(구)")
    private String userResidenceGu;

    @Column(name = "중위소득")
    private String medianIncome;

    @Column(name = "자녀유무")
    private String hasChildren;

    @Column(name = "직업 및 학업 상태")
    private String jobAndStudyStatus;

    @Column(name = "근로상태여부")
    private String employmentCondition;

    @Column(name = "가족 형태")
    private String familyType;

    @Column(name = "가구 형태")
    private String householdType;

    @Column(name = "장애인/질병")
    private String disabledOrDisease;

    @Column(name = "복지대상자")
    private String welfareTarget;
}
