package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.repository.UserInfoRepository;
import com.security.service.entity.UserInfo;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserInfoRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findByName(username).orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	public UserInfo addUser(UserInfo userInfo) {
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		return repo.save(userInfo);
	}

}
