package ar.com.freesalesview.client.utils;

import ar.com.freesalesview.client.factories.ClientFactory;
import ar.com.freesalesview.client.factories.PresenterFactory;
import ar.com.freesalesview.client.i18n.Messages;

import com.google.gwt.core.client.GWT;

public class UtilClient {
	
	private static UtilClient utilClient;

	private ClientFactory clientFactory;
	
	private Messages messages;
	
	private InitializeJS initializeJS;
	
	private UtilClient(){
		 clientFactory = GWT.create(ClientFactory.class);
		 messages = GWT.create(Messages.class);
	}
	
	public static UtilClient getInstance(){
		if (utilClient == null){
			utilClient = new UtilClient();
		}
		return utilClient;
	}
	
	public ClientFactory getClientFactory() {
		return clientFactory;
	}
	
	public void instancePresenters(){
		PresenterFactory.getInstance();
	}
	
	public Messages getMessages() {
		return messages;
	}
	
	public void instanceInitialize() {
		this.initializeJS = new InitializeJS();
	}
	
	public InitializeJS getInitializeJS() {
		return initializeJS;
	}
}
