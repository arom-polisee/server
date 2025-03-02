package com.arom_policy.demo.login.repository;

import com.arom_policy.demo.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(Long kakaoId);
    boolean existsByKakaoId(Long kakaoId);
}