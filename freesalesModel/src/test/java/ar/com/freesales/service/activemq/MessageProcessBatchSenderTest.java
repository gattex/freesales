package ar.com.freesales.service.activemq;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations={"/ar/com/freesales/freesalesApplicationContextTest.xml"})
public class MessageProcessBatchSenderTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	MessageProcessBatchSender messageSender;

	@Test
	public void sendMessage() {
		Map<String,Object> map =  new HashMap<String,Object>();
		map.put("idBatch", 1504L);
		map.put("author","gattex");
		messageSender.send(map);
	}
}
