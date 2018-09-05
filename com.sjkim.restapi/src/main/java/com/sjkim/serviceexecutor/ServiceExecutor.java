package com.sjkim.serviceexecutor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.sjkim.util.BeanUtils;

@Component
public class ServiceExecutor {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public void executeService(ServiceJob serviceJob) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("serviceName : " + serviceJob.getServiceName());
			logger.debug("methodName : " + serviceJob.getMethodName());
			logger.debug(serviceJob.getDto().toString());
			logger.debug("invokeBeanId : " + serviceJob.getInvokeBeanId());
		}
		
		Class<?> service = Class.forName(serviceJob.getServiceName());		
		Class<?> dto = Class.forName(ClassUtils.getUserClass(serviceJob.getDto()).getName());
		Method m = service.getMethod(serviceJob.getMethodName(), dto);
		m.invoke(BeanUtils.getBean(serviceJob.getInvokeBeanId()), serviceJob.getDto());
	}
}
