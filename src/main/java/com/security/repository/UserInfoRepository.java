package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.service.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{

	Optional<UserInfo> findByName(String name);

}
