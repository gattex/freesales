package ar.com.freesalesview.client.views.commons;

import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.dtos.RoomDTO;
import ar.com.freesalesview.client.enums.ActionEnum;
import ar.com.freesalesview.client.events.GroupSelectedEvent;
import ar.com.freesalesview.client.factories.ClientFactory;
import ar.com.freesalesview.client.utils.UtilClient;
import ar.com.freesalesview.client.utils.widgets.UlListPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GroupRoomWidgetUIView extends Composite{

	private static RoomWidgetUIViewUiBinder uiBinder = GWT
			.create(RoomWidgetUIViewUiBinder.class);

	interface RoomWidgetUIViewUiBinder extends
			UiBinder<Widget, GroupRoomWidgetUIView> {
	}
	
	@UiField
    UlListPanel roomsUI;
	
	@UiField
	InputElement checkGroupName;
	
	@UiField
	LabelElement groupName;
	
	private GroupRoomDTO groupRoomDTO;
	
	private ActionEnum action;
	
	public GroupRoomWidgetUIView(GroupRoomDTO room, ActionEnum action) {
		initWidget(uiBinder.createAndBindUi(this));
		this.groupRoomDTO = room;
		this.groupName.setInnerText(room.getCategory());
		this.action = action;
		chargeWidget(room);
		chargeEvents();
	}

	private void chargeWidget(GroupRoomDTO groups) {
		RoomWidgetUIView roomWidgetUIView=null;
		for (RoomDTO roomDTO : groups.getSubcategories()) {
			roomWidgetUIView = new RoomWidgetUIView(roomDTO,action);
			roomsUI.add(roomWidgetUIView);
		}
	}
	
	private void chargeEvents() {
		Event.sinkEvents(this.checkGroupName, Event.ONCHANGE);
		Event.setEventListener(this.checkGroupName, new EventListener(){
			@Override
			public void onBrowserEvent(Event event) {
				if(Event.ONCHANGE == event.getTypeInt()) {
		            onClickCheckGroupRoom(event);
		        }
			}
		});
	}
	
	public void onClickCheckGroupRoom(Event event){
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		GroupSelectedEvent groupSelectedEvent = new GroupSelectedEvent();
		groupSelectedEvent.setGroupRoomDto(this.groupRoomDTO);
		if (this.checkGroupName.isChecked()){
			groupSelectedEvent.setWasSelected(true);
		}
		groupSelectedEvent.setAction(action);
		clientFactory.getEventBus().fireEvent(groupSelectedEvent);
	}
}
