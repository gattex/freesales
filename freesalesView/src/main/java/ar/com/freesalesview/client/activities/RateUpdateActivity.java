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
import ar.com.freesalesview.client.views.rate.RateUpdateView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class RateUpdateActivity extends AbstractActivity implements RateUpdateView.Presenter, OtaSelectedHandler,GroupSelectedHandler,RoomSelectedHandler{
	
	private List<OtaDTO> selectedOtas;
	
	private List<RoomDTO> selectedRooms;
	
	private List<GroupRoomDTO> selectedGroupRooms;
	
	private List<Date> selectedDates;
	
	final ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();

	public RateUpdateActivity() {
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
		final RateUpdateView rateUpdateView = clientFactory.getRateUpdateView();
		rateUpdateView.setPresenter(this);
		panel.setWidget(rateUpdateView.asWidget());
		if (!rateUpdateView.wasChargeView()){
			Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {
				@Override
				public boolean execute() {
					HotelDTO hotel = ClientContext.getInstance().getHotel();
					if (hotel != null){
						rateUpdateView.chargeOtas(hotel.getOtasByAction(ActionEnum.RATE_UPDATE));
						rateUpdateView.chargeRooms(hotel.getRoomByAction(ActionEnum.RATE_UPDATE));
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
		clientFactory.getPlaceController().goTo(place);
	}

	/** Si la ota fue seleccionada, se agrega a la lista de lo contario se elimina*/
	@Override
	public void onCheckOta(OtaDTO otaDTO, Boolean wasSelected, ActionEnum actionEnum) {
		if (ActionEnum.RATE_UPDATE.equals(actionEnum)){
			if (wasSelected){ 
				this.selectedOtas.add(otaDTO);
			}else{
				this.selectedOtas.remove(otaDTO);
			}
		}
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
	public void selectAllOtas(boolean hasSelected) {
		this.selectedOtas.clear();
		if (hasSelected){
			HotelDTO hotel = ClientContext.getInstance().getHotel();
			this.selectedOtas.addAll(hotel.getOtasByAction(ActionEnum.RATE_UPDATE));
		}
	}
	
	@Override
	public void selectAllRooms(boolean hasSelected) {
		this.selectedRooms.clear();
		this.selectedGroupRooms.clear();
		if (hasSelected){
			HotelDTO hotel = ClientContext.getInstance().getHotel();
			List<GroupRoomDTO> groups = hotel.getRoomByAction(ActionEnum.RATE_UPDATE);
			this.selectedGroupRooms.addAll(groups);
			for (GroupRoomDTO groupRoomDTO : groups) {
				this.selectedRooms.addAll(groupRoomDTO.getSubcategories());
			}
		}
	}


	@Override
	public void doChangeRate(String dates, Double price) {
		
		String[] arrayDates = dates.split(",");
		HotelDTO hotel = ClientContext.getInstance().getHotel();
		WaitBox.getInstance().show();
		ServiceLocator.getHotelrpcservice().doChangeRate(hotel.getId(),
				this.getSelectedRooms(), this.getSelectedOtas(), this.getSelectedGroupRooms(),
				Arrays.asList(arrayDates), price,
				new DefaultWaitCallback<ResultChangeDTO>() {
					@Override
					public void success(ResultChangeDTO result) {
						clearData(); //Borro la data que fué seleccionada
						RateUpdateView rateUpdateView = clientFactory.getRateUpdateView();
						rateUpdateView.hideInfoPopup(); //Cierro el popup de información
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
	public void onCheckRoom(RoomDTO roomDTO, Boolean wasSelected, ActionEnum actionEnum) {
		if (ActionEnum.RATE_UPDATE.equals(actionEnum)){
			if (wasSelected){
				this.selectedRooms.add(roomDTO);
			}else{
				this.selectedRooms.remove(roomDTO);
			}
		}
	}


	@Override
	public void onCheckGroupRoom(GroupRoomDTO groupRoom, Boolean wasSelected,ActionEnum actionEnum) {
		if (ActionEnum.RATE_UPDATE.equals(actionEnum)){
			if (wasSelected){ 
				this.selectedGroupRooms.add(groupRoom);
			}else{
				this.selectedGroupRooms.remove(groupRoom);
			}
		}
	}


	@Override
	public List<GroupRoomDTO> getSelectedGroupRooms() {
		return this.selectedGroupRooms;
	}
	
	private void clearData(){
		this.selectedDates.clear();
		this.selectedGroupRooms.clear();
		this.selectedOtas.clear();
		this.selectedRooms.clear();
	}
}
