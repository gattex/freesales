package ar.com.freesalesview.client.events;

import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.enums.ActionEnum;
import ar.com.freesalesview.client.handlers.GroupSelectedHandler;

import com.google.gwt.event.shared.GwtEvent;

public class GroupSelectedEvent extends GwtEvent<GroupSelectedHandler> {

	public static Type<GroupSelectedHandler> TYPE = new Type<GroupSelectedHandler>();
	
	private GroupRoomDTO groupRoomDto;
	
	private Boolean wasSelected;
	
	private ActionEnum action;
	
	public GroupSelectedEvent() {
		this.wasSelected = false;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GroupSelectedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GroupSelectedHandler handler) {
		handler.onCheckGroupRoom(groupRoomDto, wasSelected,action);
	}
	
	public void setGroupRoomDto(GroupRoomDTO groupRoomDto) {
		this.groupRoomDto = groupRoomDto;
	}
	
	public void setWasSelected(Boolean wasSelected) {
		this.wasSelected = wasSelected;
	}
	
	public void setAction(ActionEnum action) {
		this.action = action;
	}
}
