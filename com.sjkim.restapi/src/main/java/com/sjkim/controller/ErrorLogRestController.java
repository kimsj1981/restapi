package com.sjkim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjkim.service.ErrorLogService;
import com.sjkim.vo.ErrorLogVo;

@RestController
@RequestMapping("errorlogs")
public class ErrorLogRestController {
	
	@Autowired
	ErrorLogService errorLogService;

	@GetMapping
	public List<ErrorLogVo> getErrorLogs() {
		return errorLogService.findErrorLogList();
	}
}
