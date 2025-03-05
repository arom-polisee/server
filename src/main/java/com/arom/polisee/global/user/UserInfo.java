package com.arom.polisee.global.user;


import com.arom.polisee.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserInfo extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    // User와 1:1 관계, PK 공유
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 10)
    private String gender;

    private Integer age;

    @Column(name = "user_residence_a")
    private String userResidenceA; // 예: 도/광역시

    @Column(name = "user_residence_b")
    private String userResidenceB; // 예: 시/군/구

    @Column(length = 50)
    private String jobStatus;

    @Column(length = 50)
    private String jobType;

    @Column(length = 50)
    private String familyStatus;

    @Column(length = 50)
    private String disabledOrElderly;

    @Column(length = 50)
    private String welfareStatus;
}
