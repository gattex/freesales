package ar.com.freesalesview.client.handlers;

import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.enums.ActionEnum;

import com.google.gwt.event.shared.EventHandler;

public interface OtaSelectedHandler extends EventHandler{
	
	void onCheckOta(OtaDTO otaDTO, Boolean wasSelected, ActionEnum action);

}
