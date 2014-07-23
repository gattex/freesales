package ar.com.freesalesview.client.events;

import ar.com.freesalesview.client.handlers.UnitOfWorkSelectedHandler;

import com.google.gwt.event.shared.GwtEvent;

public class UnitOfWorkSelectedEvent extends GwtEvent<UnitOfWorkSelectedHandler> {

	public static Type<UnitOfWorkSelectedHandler> TYPE = new Type<UnitOfWorkSelectedHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UnitOfWorkSelectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UnitOfWorkSelectedHandler handler) {
		handler.onUnitOfWorkSelected();
	}
}
