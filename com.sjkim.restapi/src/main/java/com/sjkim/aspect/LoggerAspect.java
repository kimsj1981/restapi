package com.sjkim.aspect;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjkim.advice.GlobalExceptionAdvice;
import com.sjkim.annotation.Loggable;
import com.sjkim.common.LogLevel;
import com.sjkim.common.LogType;
import com.sjkim.dto.ErrorLogDto;
import com.sjkim.service.ErrorLogService;

@Aspect
@Component
public class LoggerAspect {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ErrorLogService errorLogService;

	@AfterThrowing(pointcut = "@annotation(com.sjkim.annotation.Loggable)", throwing = "e")
	public void loggerAdvice(JoinPoint joinPoint, Exception e) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Loggable loggable = signature.getMethod().getAnnotation(Loggable.class);
		LogLevel logLevel = loggable.logLevel();
		LogType logType = loggable.logType();

		if (logger.isDebugEnabled()) {
			logger.debug("logLevel : " + logLevel + ", logType : " + logType);
			logger.debug("isWritable : " + isWritable(logLevel));
		}

		if (isWritable(logLevel)) {
			switch (logType) {
			case SELLER:
				ErrorLogDto errorLogDto = new ErrorLogDto();
				errorLogDto.setErrDt(new Date());
				errorLogDto.setErrCd(GlobalExceptionAdvice.getResponseCode(e).toString());
				errorLogService.addErrorLog(errorLogDto);
				break;
			case GENERAL:
				break;
			}
		}
	}

	private Boolean isWritable(LogLevel logLevel) {
		Boolean isWritable = Boolean.FALSE;
		switch (logLevel) {
		case TRACE:
			isWritable = logger.isTraceEnabled();
			break;
		case DEBUG:
			isWritable = logger.isTraceEnabled() || logger.isDebugEnabled();
			break;
		case INFO:
			isWritable = logger.isTraceEnabled() || logger.isDebugEnabled() || logger.isInfoEnabled();
			break;
		case WARNING:
			isWritable = logger.isTraceEnabled() || logger.isDebugEnabled() || logger.isInfoEnabled()
					|| logger.isWarnEnabled();
			break;
		case ERROR:
			isWritable = logger.isTraceEnabled() || logger.isDebugEnabled() || logger.isInfoEnabled()
					|| logger.isWarnEnabled() || logger.isErrorEnabled();
			break;
		}
		return isWritable;
	}
}
