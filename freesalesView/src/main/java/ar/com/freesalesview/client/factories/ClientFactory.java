package ar.com.freesalesview.client.factories;

import ar.com.freesalesview.client.views.availability.AvailabilityView;
import ar.com.freesalesview.client.views.dashboard.DashboardView;
import ar.com.freesalesview.client.views.dashboard.notifications.DashboardNotificationUIView;
import ar.com.freesalesview.client.views.dashboard.unitOfWork.DashboardUnitOfWorkMainContentUIView;
import ar.com.freesalesview.client.views.header.HeaderUIView;
import ar.com.freesalesview.client.views.openclose.OpenCloseView;
import ar.com.freesalesview.client.views.rate.RateUpdateView;
import ar.com.freesalesview.client.views.settings.SettingsView;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	
	EventBus getEventBus();
	
	PlaceController getPlaceController();
	
	DashboardView getDashboardView();
	
	DashboardUnitOfWorkMainContentUIView getDashboardMainContentUIView();
	
	DashboardNotificationUIView getDashboardNotificationUIView();
	
	HeaderUIView getHeaderUIView();
	
	RateUpdateView getRateUpdateView();
	
	AvailabilityView getAvailabilityView();
	
	OpenCloseView getOpenCloseView();
	
	SettingsView getSettingsView();
	
}
