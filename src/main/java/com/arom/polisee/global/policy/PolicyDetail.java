package com.arom.polisee.global.policy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class PolicyDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name", nullable = false, length = 100)
    private String serviceName;

    @Column(length = 10)
    private String gender;

    private Integer age;

    @Column(name = "residence_a")
    private String residenceA;

    @Column(name = "residence_b")
    private String residenceB;

    private String jobStatus;

    private String jobType;

    private String familyStatus;

    private String disabledOrElderly;

    private String welfareStatus;

    @OneToMany(mappedBy = "policyDetail", cascade = CascadeType.ALL)
    private List<UserRecommendPolicy> recommendPolicies;
}
