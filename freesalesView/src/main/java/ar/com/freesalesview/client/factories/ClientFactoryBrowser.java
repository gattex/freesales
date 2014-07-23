package ar.com.freesalesview.client.factories;

import ar.com.freesalesview.client.views.availability.AvailabilityUIView;
import ar.com.freesalesview.client.views.availability.AvailabilityView;
import ar.com.freesalesview.client.views.dashboard.DashboardUIView;
import ar.com.freesalesview.client.views.dashboard.DashboardView;
import ar.com.freesalesview.client.views.dashboard.notifications.DashboardNotificationUIView;
import ar.com.freesalesview.client.views.dashboard.unitOfWork.DashboardUnitOfWorkMainContentUIView;
import ar.com.freesalesview.client.views.header.HeaderUIView;
import ar.com.freesalesview.client.views.openclose.OpenCloseUIView;
import ar.com.freesalesview.client.views.openclose.OpenCloseView;
import ar.com.freesalesview.client.views.rate.RateUpdateUIView;
import ar.com.freesalesview.client.views.rate.RateUpdateView;
import ar.com.freesalesview.client.views.settings.SettingsUIView;
import ar.com.freesalesview.client.views.settings.SettingsView;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryBrowser implements ClientFactory {
	
	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final DashboardView dashboardUIView = new DashboardUIView();
	private final DashboardNotificationUIView dashboardNotificationUIView = new DashboardNotificationUIView();
	private final DashboardUnitOfWorkMainContentUIView dashboardMainContentUIView = new DashboardUnitOfWorkMainContentUIView();
	private final HeaderUIView headerUIView = new HeaderUIView();
	private final RateUpdateView rateUpdateUIView = new RateUpdateUIView();
	private final AvailabilityView availabilityView = new AvailabilityUIView();
	private final OpenCloseView openCloseView = new OpenCloseUIView();
	private final SettingsView settingsView = new SettingsUIView();
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}
	
	@Override
	public HeaderUIView getHeaderUIView() {
		return headerUIView;
	}

	@Override
	public DashboardView getDashboardView() {
		return dashboardUIView;
	}

	@Override
	public RateUpdateView getRateUpdateView() {
		return rateUpdateUIView;
	}
	
	@Override
	public AvailabilityView getAvailabilityView() {
		return availabilityView;
	}
	
	@Override
	public OpenCloseView getOpenCloseView() {
		return openCloseView;
	}

	@Override
	public SettingsView getSettingsView() {
		return settingsView;
	}

	@Override
	public DashboardUnitOfWorkMainContentUIView getDashboardMainContentUIView() {
		return dashboardMainContentUIView;
	}

	@Override
	public DashboardNotificationUIView getDashboardNotificationUIView() {
		return dashboardNotificationUIView;
	}
}
