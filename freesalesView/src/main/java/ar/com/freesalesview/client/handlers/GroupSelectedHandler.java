package ar.com.freesalesview.client.handlers;

import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.enums.ActionEnum;

import com.google.gwt.event.shared.EventHandler;

public interface GroupSelectedHandler extends EventHandler{
	
	void onCheckGroupRoom(GroupRoomDTO groupRoom, Boolean wasSelected, ActionEnum action);

}
