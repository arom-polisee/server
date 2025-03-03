package com.arom_policy.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Policy {
    @Id
    @Column(name="policy_id")
    private String id;

    @Column(name="policy_name",nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name="policy_detail")
    private PolicyDetail policyDetail;

    protected Policy() {
    }

    public Policy(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
