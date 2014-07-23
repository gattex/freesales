package ar.com.freesalesview.client.activities;

import ar.com.freesalesview.client.events.js.GwtReadyEvent;
import ar.com.freesalesview.client.factories.ClientFactory;
import ar.com.freesalesview.client.utils.UtilClient;
import ar.com.freesalesview.client.views.settings.SettingsView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SettingsActivity extends AbstractActivity implements SettingsView.Presenter{

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		SettingsView settingsView = clientFactory.getSettingsView();
		settingsView.setPresenter(this);
		panel.setWidget(settingsView.asWidget());
		GwtReadyEvent ready = new GwtReadyEvent();
		clientFactory.getEventBus().fireEvent(ready);
	}
	
	@Override
	public void goTo(Place place) {
		ClientFactory clientFactory = UtilClient.getInstance().getClientFactory();
		clientFactory.getPlaceController().goTo(place);
	}
}
