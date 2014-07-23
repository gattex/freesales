package ar.com.freesalesview.client.activities;

import ar.com.freesalesview.client.events.js.GwtReadyEvent;
import ar.com.freesalesview.client.factories.ClientFactory;
import ar.com.freesalesview.client.utils.UtilClient;
import ar.com.freesalesview.client.views.dashboard.DashboardView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DashboardActivity extends AbstractActivity implements DashboardView.Presenter{

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		DashboardView dashboard = clientFactory.getDashboardView();
		dashboard.getMainPanel().setWidget(clientFactory.getDashboardMainContentUIView());
		dashboard.getNotificationPanel().setWidget(clientFactory.getDashboardNotificationUIView());
		dashboard.setPresenter(this);
		panel.setWidget(dashboard.asWidget());
		GwtReadyEvent ready = new GwtReadyEvent();
		clientFactory.getEventBus().fireEvent(ready);
	}
	
	@Override
	public void goTo(Place place) {
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		clientFactory.getPlaceController().goTo(place);
	}
}
