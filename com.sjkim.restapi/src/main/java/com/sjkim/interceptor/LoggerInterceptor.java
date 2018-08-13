package com.sjkim.interceptor;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(getClass());

	private long startTime;

	private long endTime;

	private String params;

	private String requestUrl;

	private String lineSeparator = System.lineSeparator();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (logger.isDebugEnabled()) {
			this.startTime = System.currentTimeMillis();

			StringBuffer buffer = new StringBuffer();
			Iterator<?> iterator = request.getParameterMap().entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<?, ?> mapEntry = (Entry<?, ?>) iterator.next();
				buffer.append(mapEntry.getKey() + "="
						+ ToStringBuilder.reflectionToString(mapEntry.getValue(), ToStringStyle.SIMPLE_STYLE) + ",");
			}
			this.params = buffer.toString();

			this.requestUrl = "";
			this.requestUrl += request.getMethod() + " ";
			this.requestUrl += request.getRequestURL();
			this.requestUrl += request.getQueryString() != null ? "?" + request.getQueryString() : "";

			StringBuffer logStr = new StringBuffer();
			logStr.append(System.lineSeparator());
			logStr.append("**************************** Request Info ******************************");
			logStr.append(this.lineSeparator);
			logStr.append(" URL    		: {}");
			logStr.append(this.lineSeparator);
			logStr.append(" PARAMS 		: {}");
			logStr.append(this.lineSeparator);
			logStr.append(" IP     		: {}");
			logStr.append(this.lineSeparator);
			logStr.append(" REFERER		: {}");
			logStr.append(this.lineSeparator);
			logStr.append(" DATETIME   		: {}");
			logStr.append(this.lineSeparator);
			logStr.append("************************************************************************");

			Object[] logParam = { this.requestUrl, this.params, request.getRemoteAddr(), request.getHeader("referer"),
					new Timestamp(System.currentTimeMillis()).toString() };
			logger.debug(logStr.toString(), logParam);
		}
		return Boolean.TRUE;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (logger.isDebugEnabled()) {
			this.endTime = System.currentTimeMillis();
			StringBuffer logStr = new StringBuffer();
			logStr.append(this.lineSeparator);
			logStr.append("*************************  Process Info *********************************");
			logStr.append(this.lineSeparator);
			logStr.append(" URL    	: " + requestUrl);
			logStr.append(this.lineSeparator);
			logStr.append(" PROCESSTIME	: " + (endTime - startTime) + " ms");
			if (modelAndView != null && logger.isTraceEnabled()) {
				logStr.append(this.lineSeparator);
				logStr.append(" RESULT       : " + modelAndView.getModel().toString());
			}
			logStr.append(this.lineSeparator);
			logStr.append("*************************************************************************");
			logger.debug(logStr.toString());
		}
	}
}
