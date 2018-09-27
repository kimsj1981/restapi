package com.sjkim.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.selectUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("User Name Not Found : " + username);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("userName : " + user.getUsername());
			logger.debug("password : " + user.getPassword());
		}
		return user;
	}
}
