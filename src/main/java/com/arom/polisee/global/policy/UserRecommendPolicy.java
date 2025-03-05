package com.arom.polisee.global.policy;

import com.arom.polisee.global.entity.BaseEntity;
import com.arom.polisee.global.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class UserRecommendPolicy extends BaseEntity {

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
    private PolicyDetail policyDetail;

    // 추가 칼럼 예시
    @Column(name = "recommend_reason")
    private String recommendReason;

    @Column(name = "recommend_date")
    private LocalDateTime recommendDate;


}
