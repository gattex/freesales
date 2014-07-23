package ar.com.freesalesview.client.handlers;

import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO.ChangeStateDTO;

import com.google.gwt.event.shared.EventHandler;

public interface ChangeStatusEndHandler extends EventHandler{
	
	void onCheckStatusEnd(ChangeStateDTO dto);

}
