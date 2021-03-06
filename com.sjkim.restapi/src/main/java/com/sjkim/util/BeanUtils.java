package com.sjkim.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class BeanUtils {

	static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	public static Object getBean(String beanId) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

		if (applicationContext == null) {
			throw new NullPointerException("Application Context was not initialized.");
		}
		return applicationContext.getBean(beanId);
	}
}
