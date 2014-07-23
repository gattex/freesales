package ar.com.freesalesview.client.views.commons;

import ar.com.freesalesview.client.dtos.RoomDTO;
import ar.com.freesalesview.client.enums.ActionEnum;
import ar.com.freesalesview.client.events.RoomSelectedEvent;
import ar.com.freesalesview.client.factories.ClientFactory;
import ar.com.freesalesview.client.utils.UtilClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RoomWidgetUIView extends Composite{

	private static RoomWidgetUIViewUiBinder uiBinder = GWT
			.create(RoomWidgetUIViewUiBinder.class);

	interface RoomWidgetUIViewUiBinder extends
			UiBinder<Widget, RoomWidgetUIView> {
	}
	
	@UiField 
	InputElement checkRoom;
    
	@UiField 
	LabelElement roomName;
	
	private RoomDTO roomDTO;
	
	private ActionEnum action;
	
	public RoomWidgetUIView(RoomDTO room, ActionEnum action) {
		initWidget(uiBinder.createAndBindUi(this));
		this.roomDTO = room;
		this.action = action;
		chargeWidget();
		chargeEvents();
	}

	private void chargeWidget() {
		this.roomName.setInnerText(this.roomDTO.getSubcategory());
	}

	private void chargeEvents() {
		Event.sinkEvents(this.checkRoom, Event.ONCHANGE);
		Event.setEventListener(this.checkRoom, new EventListener(){
			@Override
			public void onBrowserEvent(Event event) {
				if(Event.ONCHANGE == event.getTypeInt()) {
		            onClickCheckRoom(event);
		        }
			}
		});
	}
	
	public void onClickCheckRoom(Event event){
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		RoomSelectedEvent roomSelectedEvent = new RoomSelectedEvent();
		roomSelectedEvent.setRoomDto(this.roomDTO);
		if (this.checkRoom.isChecked()){
			roomSelectedEvent.setWasSelected(true);
		}
		roomSelectedEvent.setAction(action);
		clientFactory.getEventBus().fireEvent(roomSelectedEvent);
	}
}
