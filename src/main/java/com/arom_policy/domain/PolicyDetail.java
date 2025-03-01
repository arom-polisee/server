package com.arom_policy.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PolicyDetail {
    @Id
    @Column(name="policyDetail_id")
    private String id;

    @OneToOne(mappedBy= "policyDetail", fetch = FetchType.LAZY)
    private Policy policy;
}
