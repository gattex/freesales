package ar.com.freesalesview.client.services;

import java.util.List;

import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.dtos.HotelDTO;
import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.dtos.ResultChangeDTO;
import ar.com.freesalesview.client.dtos.RoomDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HotelRPCServiceAsync {

	void getHotel(AsyncCallback<HotelDTO> callback);

	void doChangeRate(Long idHotel, List<RoomDTO> rooms, List<OtaDTO> otas, List<GroupRoomDTO> groupRooms,
			List<String> dates, Double value,
			AsyncCallback<ResultChangeDTO> callback);
	
	void doChangeAvailability(Long idHotel, List<RoomDTO> rooms, List<OtaDTO> otas, List<GroupRoomDTO> groupRooms,
			List<String> dates, Long numberAvailability,
			AsyncCallback<ResultChangeDTO> callback);
	
	void doChangeOpenClose(Long idHotel, List<RoomDTO> rooms, List<OtaDTO> otas, List<GroupRoomDTO> groupRooms,
			List<String> dates, boolean isOpen,
			AsyncCallback<ResultChangeDTO> callback);

}
