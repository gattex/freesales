package ar.com.freesalesview.client.views.availability;

import java.util.List;

import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.dtos.RoomDTO;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface AvailabilityView extends IsWidget {

void setPresenter(Presenter presenter);
	
	void chargeOtas(List<OtaDTO> otas);
	
	void chargeRooms(List<GroupRoomDTO> rooms);
	
	boolean wasChargeView();
	
	void hideInfoPopup();

	public interface Presenter {
		void goTo(Place place);
		List<OtaDTO> getSelectedOtas();
		List<RoomDTO> getSelectedRooms();
		List<GroupRoomDTO> getSelectedGroupRooms();
		void selectAllOtas(boolean hasSelected);
		void selectAllRooms(boolean hasSelected);
		void doChangeAvailability(String dates, Long numberAvailability);
		List<String> getShowPreSelectedDates(String dates);
	}
}
