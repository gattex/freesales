package ar.com.freesalesprocess;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

public class MessageReceiver implements MessageListener {
	
	private Logger log = Logger.getLogger(MessageReceiver.class);

	public void onMessage(final Message message) {
		try{
			log.info("entro " + message.toString());
		}catch (Exception e) {
			log.error(e,e);
		}
	}
}
