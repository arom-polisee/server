package com.arom.polisee.domain.recommend_policy;

import com.arom.polisee.domain.policy_requirements.PolicyRequirements;
import com.arom.polisee.global.entity.BaseEntity;
import com.arom.polisee.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "recommend_policy")
public class RecommendPolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long recommendId;

    // 어떤 User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 어떤 PolicyDetail
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private PolicyRequirements policyDetail;




}
