package com.sjkim.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

import com.sjkim.serviceexecutor.ServiceJob;

public class JmsSender {

	@Value("${queue.destination}")
	private String destination;

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void send(ServiceJob serviceExecutor) {
		jmsTemplate.convertAndSend(destination, serviceExecutor);
	}
}
