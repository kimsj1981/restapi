package com.sjkim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sjkim.dto.ErrorLogDto;
import com.sjkim.repository.ErrorLogDao;
import com.sjkim.vo.ErrorLogVo;

@Service
public class ErrorLogServiceImpl implements ErrorLogService {

	@Autowired
	private ErrorLogDao errorLogDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer addErrorLog(ErrorLogDto errorLogDto) {
		return errorLogDao.insertErrorLog(errorLogDto);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ErrorLogVo> findErrorLogList() {
		return errorLogDao.selectErrorLogAll();
	}
}