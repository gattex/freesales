package ar.com.freesalesview.client.services;

import java.util.List;

import ar.com.freesalesview.client.dtos.UnitOfWorkDTO;
import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO.ChangeStateDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("notificationRPCService")
public interface NotificationRPCService extends RemoteService{
	
	public List<UnitOfWorkDTO> getLastUnitsOfWorks(Long idHotel);
	
	public ChangeStateDTO checkStatus(Long idHotel, ChangeStateDTO dto);
	
	
}
