package com.sjkim.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


public class PropertyMessage {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
		
	public Boolean isQueueUse() {
		Boolean queueUse = Boolean.valueOf(messageSource.getMessage("queue.use", new Object[0], new Locale("en")));
		if (logger.isDebugEnabled()) {
			logger.debug("queueUse : " + queueUse);
		}
		return queueUse;
	}
}