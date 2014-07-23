package ar.com.freesalesview.client.services;

import java.util.List;

import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.dtos.HotelDTO;
import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.dtos.ResultChangeDTO;
import ar.com.freesalesview.client.dtos.RoomDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("hotelRPCService")
public interface HotelRPCService extends RemoteService{
	
	HotelDTO getHotel();
	
	ResultChangeDTO doChangeRate(Long idHotel, List<RoomDTO> rooms,
			List<OtaDTO> otas, List<GroupRoomDTO> groupRooms,
			List<String> dates, Double value);

	ResultChangeDTO doChangeAvailability(Long idHotel, List<RoomDTO> rooms,
			List<OtaDTO> otas, List<GroupRoomDTO> groupRooms,
			List<String> dates, Long numberAvailability);

	ResultChangeDTO doChangeOpenClose(Long idHotel, List<RoomDTO> rooms,
			List<OtaDTO> otas, List<GroupRoomDTO> groupRooms,
			List<String> dates, boolean isOpen);
	
}
