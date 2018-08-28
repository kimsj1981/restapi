package com.sjkim.serviceexecutor;

import com.sjkim.base.BaseObject;

public class ServiceJob extends BaseObject {

	private static final long serialVersionUID = -6392991462075762159L;
	
	private String serviceName;

	private String methodName;

	private Object dto;

	private String invokeBeanId;
			
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object getDto() {
		return dto;
	}

	public void setDto(Object dto) {
		this.dto = dto;
	}

	public String getInvokeBeanId() {
		return invokeBeanId;
	}

	public void setInvokeBeanId(String invokeBeanId) {
		this.invokeBeanId = invokeBeanId;
	}
}
