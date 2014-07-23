package ar.com.freesalesview.client.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.dtos.HotelDTO;
import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.dtos.ResultChangeDTO;
import ar.com.freesalesview.client.dtos.RoomDTO;
import ar.com.freesalesview.client.enums.ActionEnum;
import ar.com.freesalesview.client.events.GroupSelectedEvent;
import ar.com.freesalesview.client.events.OtaSelectedEvent;
import ar.com.freesalesview.client.events.RoomSelectedEvent;
import ar.com.freesalesview.client.events.UnitOfWorkSelectedEvent;
import ar.com.freesalesview.client.events.js.GwtReadyEvent;
import ar.com.freesalesview.client.factories.ClientFactory;
import ar.com.freesalesview.client.handlers.GroupSelectedHandler;
import ar.com.freesalesview.client.handlers.OtaSelectedHandler;
import ar.com.freesalesview.client.handlers.RoomSelectedHandler;
import ar.com.freesalesview.client.places.DashboardPlace;
import ar.com.freesalesview.client.services.utils.DefaultWaitCallback;
import ar.com.freesalesview.client.services.utils.ServiceLocator;
import ar.com.freesalesview.client.utils.ClientContext;
import ar.com.freesalesview.client.utils.DateUtils;
import ar.com.freesalesview.client.utils.UtilClient;
import ar.com.freesalesview.client.utils.widgets.WaitBox;
import ar.com.freesalesview.client.views.availability.AvailabilityView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AvailabilityActivity extends AbstractActivity implements AvailabilityView.Presenter,OtaSelectedHandler,GroupSelectedHandler,RoomSelectedHandler{

	private List<OtaDTO> selectedOtas;
	
	private List<RoomDTO> selectedRooms;
	
	private List<GroupRoomDTO> selectedGroupRooms;
	
	private List<Date> selectedDates;
	
	final ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
	
	
	public AvailabilityActivity() {
		clientFactory.getEventBus().addHandler(OtaSelectedEvent.TYPE, this);
		clientFactory.getEventBus().addHandler(GroupSelectedEvent.TYPE, this);
		clientFactory.getEventBus().addHandler(RoomSelectedEvent.TYPE, this);
		this.selectedOtas = new ArrayList<OtaDTO>();
		this.selectedRooms = new ArrayList<RoomDTO>();
		this.selectedGroupRooms = new ArrayList<GroupRoomDTO>();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		WaitBox.getInstance().show();
		final AvailabilityView availabilityView = clientFactory.getAvailabilityView();
		availabilityView.setPresenter(this);
		panel.setWidget(availabilityView.asWidget());
		if (!availabilityView.wasChargeView()){
			Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {
				@Override
				public boolean execute() {
					HotelDTO hotel = ClientContext.getInstance().getHotel();
					if (hotel != null){
						availabilityView.chargeOtas(hotel.getOtasByAction(ActionEnum.AVAILABILITY));
						availabilityView.chargeRooms(hotel.getRoomByAction(ActionEnum.AVAILABILITY));
						GwtReadyEvent ready = new GwtReadyEvent();
						clientFactory.getEventBus().fireEvent(ready);
						WaitBox.getInstance().hide();
						return false;
					}
					return true;
				}
			});
		}else{
			WaitBox.getInstance().hide();
		}
	}
	
	@Override
	public void goTo(Place place) {
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public List<OtaDTO> getSelectedOtas() {
		return this.selectedOtas;
	}

	@Override
	public List<RoomDTO> getSelectedRooms() {
		return this.selectedRooms;
	}

	@Override
	public List<GroupRoomDTO> getSelectedGroupRooms() {
		return this.selectedGroupRooms;
	}

	@Override
	public void selectAllOtas(boolean hasSelected) {
		this.selectedOtas.clear();
		if (hasSelected){
			HotelDTO hotel = ClientContext.getInstance().getHotel();
			this.selectedOtas.addAll(hotel.getOtasByAction(ActionEnum.AVAILABILITY));
		}
	}
	
	@Override
	public void selectAllRooms(boolean hasSelected) {
		this.selectedRooms.clear();
		this.selectedGroupRooms.clear();
		if (hasSelected){
			HotelDTO hotel = ClientContext.getInstance().getHotel();
			List<GroupRoomDTO> groups = hotel.getRoomByAction(ActionEnum.AVAILABILITY);
			this.selectedGroupRooms.addAll(groups);
			for (GroupRoomDTO groupRoomDTO : groups) {
				this.selectedRooms.addAll(groupRoomDTO.getSubcategories());
			}
		}
	}

	@Override
	public void doChangeAvailability(String dates, Long numberAvailability) {
		
		String[] arrayDates = dates.split(",");
		HotelDTO hotel = ClientContext.getInstance().getHotel();
		WaitBox.getInstance().show();
		ServiceLocator.getHotelrpcservice().doChangeAvailability(hotel.getId(),
				this.getSelectedRooms(), this.getSelectedOtas(), this.getSelectedGroupRooms(),
				Arrays.asList(arrayDates), numberAvailability,
				new DefaultWaitCallback<ResultChangeDTO>() {
					@Override
					public void success(ResultChangeDTO result) {
						clearData();
						AvailabilityView availabilityView = clientFactory.getAvailabilityView();
						availabilityView.hideInfoPopup();
						clientFactory.getEventBus().fireEvent(new UnitOfWorkSelectedEvent());
						clientFactory.getPlaceController().goTo(new DashboardPlace());
					}
				});		
	}

	@Override
	public List<String> getShowPreSelectedDates(String dates) {
		String[] splitDates = dates.split(",");
		this.selectedDates = DateUtils.toDate(splitDates);
		List<List<Date>> groups = DateUtils.transformSelectedDates(selectedDates);
		List<String> toShow = new ArrayList<String>();
		for (List<Date> group : groups) {
			if (group.size() > 1){
				toShow.add(DateUtils.showStringBetweenDates(group.get(0), group.get(group.size()-1)));
			}else{
				toShow.add(DateUtils.showStringDate(group.get(0)));
			}
		}
		return toShow;
	}

	@Override
	public void onCheckRoom(RoomDTO roomDTO, Boolean wasSelected,
			ActionEnum action) {
		if (ActionEnum.AVAILABILITY.equals(action)){
			if (wasSelected){ 
				this.selectedRooms.add(roomDTO);
			}else{
				this.selectedRooms.remove(roomDTO);
			}
		}
	}

	@Override
	public void onCheckGroupRoom(GroupRoomDTO groupRoom, Boolean wasSelected,
			ActionEnum action) {
		if (ActionEnum.AVAILABILITY.equals(action)){
			if (wasSelected){ 
				this.selectedGroupRooms.add(groupRoom);
			}else{
				this.selectedGroupRooms.remove(groupRoom);
			}
		}		
	}

	@Override
	public void onCheckOta(OtaDTO otaDTO, Boolean wasSelected, ActionEnum action) {
		if (ActionEnum.AVAILABILITY.equals(action)){
			if (wasSelected){ 
				this.selectedOtas.add(otaDTO);
			}else{
				this.selectedOtas.remove(otaDTO);
			}
		}
	}
	
	private void clearData(){
		this.selectedDates.clear();
		this.selectedGroupRooms.clear();
		this.selectedOtas.clear();
		this.selectedRooms.clear();
	}
}
