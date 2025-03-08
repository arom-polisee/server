package com.arom.polisee.domain.user;

import com.arom.polisee.domain.userInfo.UserInfo;
import com.arom.polisee.global.entity.BaseEntity;
import com.arom.polisee.domain.recommend_policy.RecommendPolicy;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    // 1) user_info와 1:1
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    private UserInfo userInfo;

    // 2) user_recommend_policy 와 1:N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendPolicy> recommendPolicies = new ArrayList<>();
}



