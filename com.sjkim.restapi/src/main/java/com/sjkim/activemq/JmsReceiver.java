package com.sjkim.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import com.sjkim.serviceexecutor.ServiceExecutor;
import com.sjkim.serviceexecutor.ServiceJob;

public class JmsReceiver {
	
	@Autowired
	private ServiceExecutor serviceExecutor;
	
	@JmsListener(destination = "${queue.destination}")
	public void receive(ServiceJob serviceJob) throws Exception {		
		serviceExecutor.executeService(serviceJob);
	}
}
