package com.sjkim.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sjkim.dto.ErrorLogDto;
import com.sjkim.vo.ErrorLogVo;

@Mapper
@Repository
public interface ErrorLogDao {

	int insertErrorLog(ErrorLogDto errorLogDto);

	List<ErrorLogVo> selectErrorLogAll();
}
