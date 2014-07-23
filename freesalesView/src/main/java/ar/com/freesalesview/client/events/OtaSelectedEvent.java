package ar.com.freesalesview.client.events;

import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.enums.ActionEnum;
import ar.com.freesalesview.client.handlers.OtaSelectedHandler;

import com.google.gwt.event.shared.GwtEvent;

public class OtaSelectedEvent extends GwtEvent<OtaSelectedHandler> {

	public static Type<OtaSelectedHandler> TYPE = new Type<OtaSelectedHandler>();
	
	private OtaDTO ota;
	
	private Boolean wasSelected;
	
	private ActionEnum action;
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<OtaSelectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(OtaSelectedHandler handler) {
		handler.onCheckOta(ota,wasSelected,action);
	}
	
	public void setOta(OtaDTO ota) {
		this.ota = ota;
	}
	
	public void setWasSelected(Boolean wasSelected) {
		this.wasSelected = wasSelected;
	}
	
	public void setAction(ActionEnum action) {
		this.action = action;
	}
}
