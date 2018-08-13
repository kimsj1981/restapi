package com.sjkim.advice;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjkim.exception.CardPointNotFoundException;
import com.sjkim.vo.MessageVo;
import com.sjkim.vo.MessageVoBuilder;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionAdvice {

	Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public MessageVo handleException(Exception e) {
		if (logger.isDebugEnabled()) {
			e.printStackTrace();
		}
		
		MessageVoBuilder messageVoBuilder = new MessageVoBuilder();
		return messageVoBuilder.setRowCount(0).setContents(null).setHttpStatus(getResponseCode(e)).build();
	}

	public static Integer getResponseCode(Exception e) {
		Integer responseCode;
		if (e instanceof CardPointNotFoundException) {
			responseCode = HttpServletResponse.SC_NOT_FOUND;
		} else {
			responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		return responseCode;
	}
}
