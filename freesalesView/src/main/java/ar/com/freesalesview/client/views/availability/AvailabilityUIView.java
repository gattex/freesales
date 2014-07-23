package ar.com.freesalesview.client.views.availability;

import java.util.List;

import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.dtos.RoomDTO;
import ar.com.freesalesview.client.enums.ActionEnum;
import ar.com.freesalesview.client.utils.JSUtils;
import ar.com.freesalesview.client.utils.widgets.UlListPanel;
import ar.com.freesalesview.client.views.commons.GroupRoomWidgetUIView;
import ar.com.freesalesview.client.views.commons.OtaWidgetUIView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AvailabilityUIView extends Composite implements AvailabilityView {

	private static AvailabilityUIViewUiBinder uiBinder = GWT
			.create(AvailabilityUIViewUiBinder.class);

	interface AvailabilityUIViewUiBinder extends
			UiBinder<Widget, AvailabilityUIView> {
	}

	public AvailabilityUIView() {
		initWidget(uiBinder.createAndBindUi(this));
		this.selectedDates.setId("selectedDates");
		this.chargeEvents();
		groupRoomUI.setStyleName("rooms-check-list");
  	    groupRoomUI.getElement().setAttribute("id", "left-list");
  	    otasUI.setStyleName("rooms-check-list");
  	    otasUI.getElement().setAttribute("id", "left-list");
  	    otaCharge = false;
  	    roomCharge = false;
  	    this.popupInfo.setAttribute("id", "update-availability-popup");
	}
	
	@UiField
    UlListPanel groupRoomUI;
	
	@UiField
    UlListPanel otasUI;
	
	@UiField
	Anchor selectAllOta;
	
	@UiField
	Anchor selectAllRoom;
	
	@UiField
	AnchorElement update;
	
	@UiField
	SpanElement preSelectedOtas;
	
	@UiField
	SpanElement preSelectedGroups;
	
	@UiField
	SpanElement preSelectedRooms;
	
	@UiField
	SpanElement preSelectedDates;
	
	@UiField
	TextBox numberAvailability;
	
	@UiField
	Button submit;
	
	@UiField
	InputElement selectedDates;
	
	@UiField
	DivElement popupInfo;
	
	@UiField
	Button cancelPopupInfo;

	private boolean otaCharge;
	private boolean roomCharge;

	
	private Presenter presenter;

	
	@Override
	public void chargeOtas(List<OtaDTO> otas){
		this.otaCharge = true;
		OtaWidgetUIView otaWidget = null;
		for (OtaDTO otaDTO : otas) {
			otaWidget = new OtaWidgetUIView(otaDTO,ActionEnum.AVAILABILITY);
			otasUI.add(otaWidget);
		}
	}

	@Override
	public void chargeRooms(List<GroupRoomDTO> rooms) {
		this.roomCharge = true;
		GroupRoomWidgetUIView widget =null;
		for (GroupRoomDTO group : rooms) {
			widget = new GroupRoomWidgetUIView(group,ActionEnum.AVAILABILITY);
			groupRoomUI.add(widget);
		}
	}
	
	private void chargeEvents() {
		Event.sinkEvents(this.update, Event.ONCLICK);
		Event.setEventListener(this.update, new EventListener(){
			@Override
			public void onBrowserEvent(Event event) {
				if(Event.ONCLICK == event.getTypeInt()) {
		           onClickUpdate(event);
		        }
			}
		});
	}
	
	private void onClickUpdate(Event event){
		this.showPreSelectedDates();
		this.showPreSelectedOtas();
		this.showPreSelectedGroups();
		this.showPreSelectedRooms();
	}
	
	private void showPreSelectedOtas(){
		StringBuffer buffer = new StringBuffer();
		if (this.presenter.getSelectedOtas().size() == 0){
			this.preSelectedOtas.setInnerHTML("empty");
			return;
		}
		for (OtaDTO ota : this.presenter.getSelectedOtas()) {
			buffer.append(ota).append("<br/>");
		}
		this.preSelectedOtas.setInnerHTML(buffer.toString());
	}
	
	private void showPreSelectedRooms(){
		StringBuffer buffer = new StringBuffer();
		if (this.presenter.getSelectedRooms().size() == 0){
			this.preSelectedRooms.setInnerHTML("empty");
			return;
		}
		for (RoomDTO room : this.presenter.getSelectedRooms()) {
			buffer.append(room).append("<br/>");
		}
		this.preSelectedRooms.setInnerHTML(buffer.toString());
		
	}
	
	private void showPreSelectedGroups(){
		StringBuffer buffer = new StringBuffer();
		if (this.presenter.getSelectedGroupRooms().size() == 0){
			this.preSelectedGroups.setInnerHTML("empty");
			return;
		}
		for (GroupRoomDTO group : this.presenter.getSelectedGroupRooms()) {
			buffer.append(group).append("<br/>");
		}
		this.preSelectedGroups.setInnerHTML(buffer.toString());
		
	}
	
	private void showPreSelectedDates(){
		StringBuffer buffer = new StringBuffer();
		if (this.selectedDates.getValue()!=null && "".equalsIgnoreCase(this.selectedDates.getValue().trim())){
			this.preSelectedDates.setInnerHTML("empty");
			return;
		}
		List<String> showPreSelectedDates = this.presenter.getShowPreSelectedDates(this.selectedDates.getValue());
		for (String groupToShow : showPreSelectedDates) {
			buffer.append(groupToShow).append("<br/>");
		}
		this.preSelectedDates.setInnerHTML(buffer.toString());
	}
	
	@UiHandler("selectAllOta")
	public void onClickSelectAllOta(ClickEvent event){
		boolean deselectAll = selectAllOta.getStyleName().contains("deselect");
		this.presenter.selectAllOtas(!deselectAll);
	}
	
	@UiHandler("selectAllRoom")
	public void onClickSelectAllRoom(ClickEvent event){
		boolean deselectAll = selectAllRoom.getStyleName().contains("deselect");
		this.presenter.selectAllRooms(!deselectAll);
	}
	
	@UiHandler("submit")
	public void onClickSubmitAvailability(ClickEvent event){
		String dates = this.selectedDates.getValue();
		String value = this.numberAvailability.getValue();
		this.presenter.doChangeAvailability(dates,new Long(value));
	}
	
	@UiHandler("cancelPopupInfo")
	public void onClickCancelEdit(ClickEvent event){
		JSUtils.close(this.popupInfo);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter; 
	}

	@Override
	public boolean wasChargeView() {
		return (otaCharge && roomCharge);
	}

	@Override
	public void hideInfoPopup() {
		JSUtils.resetCalendar();
		JSUtils.deselectAll(this.selectAllRoom.getElement());
		JSUtils.deselectAll(this.selectAllOta.getElement());
		JSUtils.close(this.popupInfo);
	}
}

