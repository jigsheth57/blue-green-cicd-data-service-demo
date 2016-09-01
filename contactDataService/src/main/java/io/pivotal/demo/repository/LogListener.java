package io.pivotal.demo.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogListener {

	private static final Log log = LogFactory.getLog(LogListener.class);
	final SimpleDateFormat dx = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	private static RabbitTemplate rabbitTemplate;
	final static String queueName = "contact-change-event";

	@Autowired(required = true)
	@Qualifier("rabbitTemplate")
	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@PostPersist
    private void postCreateEvent(Object object) {
    	String message = "{\"Status\": \"PostCreate\", \"Date\": \""+dx.format(new Date())+"\", \"Contact\": "+object+"}";
    	postMessage(message);
    }
    
	@PostUpdate
    private void postUpdateEvent(Object object) {
    	String message = "{\"Status\": \"PostUpdate\", \"Date\": \""+dx.format(new Date())+"\", \"Contact\": "+object+"}";
    	postMessage(message);
    }
    
    @PostRemove
    private void postRemoveEvent(Object object) {
    	String message = "{\"Status\": \"PostRemove\", \"Date\": \""+dx.format(new Date())+"\", \"Contact\": "+object+"}";
    	postMessage(message);
    }

    private void postMessage(String message) {
/*
    	ConnectionFactory conn = rabbitTemplate.getConnectionFactory();
		log.debug("RabbitMQ Host:"+conn.getHost());
		log.debug("RabbitMQ Port:"+conn.getPort());
		log.debug("RabbitMQ VirtualHost:"+conn.getVirtualHost());
*/		
    	log.debug(message);
    	rabbitTemplate.convertAndSend(queueName, message);
    }
    
}
