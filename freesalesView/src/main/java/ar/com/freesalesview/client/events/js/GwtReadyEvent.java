package ar.com.freesalesview.client.events.js;

import com.google.gwt.event.shared.GwtEvent;

public class GwtReadyEvent extends GwtEvent<GwtReadyHandler> {

	public static Type<GwtReadyHandler> TYPE = new Type<GwtReadyHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GwtReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GwtReadyHandler handler) {
		handler.onGwtReady();
	}
}
