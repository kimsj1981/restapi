package com.sjkim.service;

import java.util.List;

import com.sjkim.dto.ErrorLogDto;
import com.sjkim.vo.ErrorLogVo;

public interface ErrorLogService {

	public Integer addErrorLog(ErrorLogDto errLogDto);

	public List<ErrorLogVo> findErrorLogList();
}
