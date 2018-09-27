package com.sjkim.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sjkim.security.User;

@Mapper
@Repository
public interface UserDao {
	
	public User selectUser(String userName);
	
	public List<String> selectAuthority(String userName);
}
