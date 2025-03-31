package com.arom.polisee.domain.userInfo.repository;

import com.arom.polisee.domain.userInfo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
