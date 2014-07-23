package ar.com.freesalesview.client.views.commons;

import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.enums.ActionEnum;
import ar.com.freesalesview.client.events.OtaSelectedEvent;
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

public class OtaWidgetUIView extends Composite{

	private static OtaWidgetUIViewUiBinder uiBinder = GWT
			.create(OtaWidgetUIViewUiBinder.class);

	interface OtaWidgetUIViewUiBinder extends
			UiBinder<Widget, OtaWidgetUIView> {
	}

	@UiField
	LabelElement otaName;
	
	@UiField
	InputElement inputCheckOta;
	
	private OtaDTO dto;
	
	private ActionEnum action;
	

	public OtaWidgetUIView(OtaDTO dto, ActionEnum action) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.action = action;
		chargeWidget();
		chargeEvents();
	}

	private void chargeWidget() {
		this.otaName.setInnerText(this.dto.getName());
	}

	private void chargeEvents() {
		Event.sinkEvents(this.inputCheckOta, Event.ONCLICK);
		Event.setEventListener(this.inputCheckOta, new EventListener(){
			@Override
			public void onBrowserEvent(Event event) {
				if(Event.ONCLICK == event.getTypeInt()) {
		            onClickCheckOta(event);
		        }
			}
		});
	}
	
	public void onClickCheckOta(Event event){
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		OtaSelectedEvent otaCheckEvent = new OtaSelectedEvent();
		otaCheckEvent.setOta(dto);
		otaCheckEvent.setWasSelected(this.inputCheckOta.isChecked());
		otaCheckEvent.setAction(action);
		clientFactory.getEventBus().fireEvent(otaCheckEvent);
	}

}
