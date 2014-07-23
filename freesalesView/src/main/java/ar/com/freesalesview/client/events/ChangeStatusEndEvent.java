package ar.com.freesalesview.client.events;

import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO.ChangeStateDTO;
import ar.com.freesalesview.client.handlers.ChangeStatusEndHandler;

import com.google.gwt.event.shared.GwtEvent;

public class ChangeStatusEndEvent extends GwtEvent<ChangeStatusEndHandler> {

	public static Type<ChangeStatusEndHandler> TYPE = new Type<ChangeStatusEndHandler>();
	
	
	private ChangeStateDTO dto;
	
	public ChangeStatusEndEvent(ChangeStateDTO dto) {
		this.dto = dto;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeStatusEndHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeStatusEndHandler handler) {
		handler.onCheckStatusEnd(dto);
	}
	
	public ChangeStateDTO getDto() {
		return dto;
	}
}
