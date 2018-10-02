package com.sjkim.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sjkim.repository.UserDao;
import com.sjkim.security.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.selectUser(userName);
		if (user == null) {
			throw new UsernameNotFoundException("User Name Not Found : " + userName);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("userName : " + user.getUsername());
			logger.debug("password : " + user.getPassword());
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<String> roles  = userDao.selectAuthority(userName);
		for (String role : roles) {
			logger.debug("role : " + role);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		user.setAuthorities(authorities);
		return user;
	}
}
