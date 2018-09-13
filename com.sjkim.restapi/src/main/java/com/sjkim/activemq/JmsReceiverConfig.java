package com.sjkim.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
@EnableJms
public class JmsReceiverConfig {

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;
	
	@Value("${queue.concurrency}")
	private String queueConcurrency;
	
	@Autowired
	private MessageConverter messageConverter;

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(brokerUrl);
		return activeMQConnectionFactory;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(activeMQConnectionFactory());
		factory.setMessageConverter(messageConverter);
		factory.setConcurrency(queueConcurrency);
		return factory;
	}

	@Bean
	public JmsReceiver receiver() {
		return new JmsReceiver();
	}
}
