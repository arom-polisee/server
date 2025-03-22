package com.arom.polisee.domain.recommend_policies;

import com.arom.polisee.domain.policies.entity.Policies;
import com.arom.polisee.domain.user.User;
import com.arom.polisee.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_user_policy",
                        columnNames = {"user_id", "service_id"}
                )
        }
)
public class RecommendPolicies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long recommendId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Policies policies;
}
