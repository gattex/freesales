package ar.com.freesalesview.client.utils;

import ar.com.freesalesview.client.events.js.GwtReadyEvent;
import ar.com.freesalesview.client.events.js.GwtReadyHandler;
import ar.com.freesalesview.client.factories.ClientFactory;

public class InitializeJS implements GwtReadyHandler {
	
	public InitializeJS() {
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		clientFactory.getEventBus().addHandler(GwtReadyEvent.TYPE,this);
	}

	@Override
	public void onGwtReady() {
		gwtReady();
	}
	
	public static native void gwtReady() /*-{
		$wnd.gwtOnReady();
	}-*/;
}
