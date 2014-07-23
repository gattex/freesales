package ar.com.freesalesview.client.views.dashboard.notifications;

import ar.com.freesalesview.client.presenters.dashboard.DashboardNotificationPresenter;
import ar.com.freesalesview.client.utils.widgets.UlListPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DashboardNotificationUIView extends Composite{

	private static DashboardNotificationUIViewUiBinder uiBinder = GWT
			.create(DashboardNotificationUIViewUiBinder.class);

	interface DashboardNotificationUIViewUiBinder extends
			UiBinder<Widget, DashboardNotificationUIView> {
	}
	
	private DashboardNotificationPresenter presenter;
	
	@UiField
    UlListPanel notificationsUl;


	public DashboardNotificationUIView() {
		initWidget(uiBinder.createAndBindUi(this));
		//TODO Deberia llamar a un metodo del presenter para que busque las notificaciones (si las hay)
	}

	
	public void setPresenter(DashboardNotificationPresenter presenter) {
		this.presenter = presenter;
	}
	
	public UlListPanel getNotificationsUl() {
		return notificationsUl;
	}
}
