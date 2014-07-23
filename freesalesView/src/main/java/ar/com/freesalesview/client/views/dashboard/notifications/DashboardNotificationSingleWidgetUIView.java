package ar.com.freesalesview.client.views.dashboard.notifications;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DashboardNotificationSingleWidgetUIView extends Composite{

	private static DashboardNotificationSingleWidgetUIViewUiBinder uiBinder = GWT
			.create(DashboardNotificationSingleWidgetUIViewUiBinder.class);

	interface DashboardNotificationSingleWidgetUIViewUiBinder extends
			UiBinder<Widget, DashboardNotificationSingleWidgetUIView> {
	}

	@UiField
	Anchor detail;
	
	@UiField
	Label notification;
	
	@UiField
	Label dateAndTime;
	
	@UiField
	DivElement divColorNotification;
	
	@UiField
	SpanElement spanIconNotification;
	
	public DashboardNotificationSingleWidgetUIView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("detail")
	public void onClickDetailLink(ClickEvent event){
		Window.alert("click en detail");
	}
	


}
