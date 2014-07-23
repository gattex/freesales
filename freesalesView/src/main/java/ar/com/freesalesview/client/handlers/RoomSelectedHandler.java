package ar.com.freesalesview.client.handlers;

import ar.com.freesalesview.client.dtos.RoomDTO;
import ar.com.freesalesview.client.enums.ActionEnum;

import com.google.gwt.event.shared.EventHandler;

public interface RoomSelectedHandler extends EventHandler{
	
	void onCheckRoom(RoomDTO roomDTO, Boolean wasSelected, ActionEnum action);

}
