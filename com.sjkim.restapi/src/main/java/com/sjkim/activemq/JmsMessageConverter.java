package com.sjkim.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.sjkim.util.SerializationUtils;

@Component
public class JmsMessageConverter implements MessageConverter {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		if (!(object instanceof Serializable)) {
			throw new IllegalArgumentException("Object should implements Serializable");
		}
		
		Message message = null;
		try {
			String base64String = SerializationUtils.toBase64String((Serializable) object);
			message = session.createObjectMessage();
			message.setStringProperty("base64String", base64String);
			if (logger.isDebugEnabled()) {
				logger.debug("convert Object to JmsMessage : " + base64String);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		Object object = null;
		try {
			object = SerializationUtils.fromBase64String(message.getStringProperty("base64String"));
			if (logger.isDebugEnabled()) {
				logger.debug("convert JmsMessage to Object: " + object.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
