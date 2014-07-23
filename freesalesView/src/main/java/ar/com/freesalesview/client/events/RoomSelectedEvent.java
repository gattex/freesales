package ar.com.freesalesview.client.events;

import ar.com.freesalesview.client.dtos.RoomDTO;
import ar.com.freesalesview.client.enums.ActionEnum;
import ar.com.freesalesview.client.handlers.RoomSelectedHandler;

import com.google.gwt.event.shared.GwtEvent;

public class RoomSelectedEvent extends GwtEvent<RoomSelectedHandler> {

	public static Type<RoomSelectedHandler> TYPE = new Type<RoomSelectedHandler>();
	
	private RoomDTO roomDto;
	
	private Boolean wasSelected;
	
	private ActionEnum action;
	
	public RoomSelectedEvent() {
		this.wasSelected = false;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RoomSelectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RoomSelectedHandler handler) {
		handler.onCheckRoom(roomDto, wasSelected,action);
	}
	
	public void setRoomDto(RoomDTO roomDto) {
		this.roomDto = roomDto;
	}
	
	public void setWasSelected(Boolean wasSelected) {
		this.wasSelected = wasSelected;
	}
	
	public void setAction(ActionEnum action) {
		this.action = action;
	}
}
