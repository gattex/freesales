package ar.com.freesalesview.client.services;

import java.util.List;

import ar.com.freesalesview.client.dtos.UnitOfWorkDTO;
import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO.ChangeStateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface NotificationRPCServiceAsync {

	void getLastUnitsOfWorks(Long idHotel,
			AsyncCallback<List<UnitOfWorkDTO>> callback);

	void checkStatus(Long idHotel, ChangeStateDTO dto,
			AsyncCallback<ChangeStateDTO> callback);


}
