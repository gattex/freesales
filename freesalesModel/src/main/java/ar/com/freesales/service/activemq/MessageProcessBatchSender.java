package ar.com.freesales.service.activemq;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessBatchSender {

	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(final Map<?,?> map) {
		jmsTemplate.convertAndSend(map);
	}
}
