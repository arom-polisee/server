package com.arom.polisee.global.login.repository;

import com.arom.polisee.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByKakaoId(Long kakaoId);

}