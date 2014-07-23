package ar.com.freesalesview.client;

 import ar.com.freesalesview.client.dtos.HotelDTO;
import ar.com.freesalesview.client.dtos.user.UserDTO;
import ar.com.freesalesview.client.events.UnitOfWorkSelectedEvent;
import ar.com.freesalesview.client.factories.AppActivityMapper;
import ar.com.freesalesview.client.factories.AppPlaceHistoryMapper;
import ar.com.freesalesview.client.places.DashboardPlace;
import ar.com.freesalesview.client.services.utils.DefaultWaitCallback;
import ar.com.freesalesview.client.services.utils.ServiceLocator;
import ar.com.freesalesview.client.utils.ClientContext;
import ar.com.freesalesview.client.utils.UtilClient;
import ar.com.freesalesview.client.utils.widgets.WaitBox;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class FreeSales  implements EntryPoint {
	
	private Place homePlace = new DashboardPlace();
	
	public void onModuleLoad() {
		WaitBox.getInstance().show();
		ServiceLocator.getUserRpcService().getUserLogged(new DefaultWaitCallback<UserDTO>() {
			@Override
			public void success(UserDTO result) {
				ClientContext.getInstance().setUser(result);
				initModule();
			}
		});
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				chargeHotelData();
			}
		});
	}

	private void chargeHotelData() {
		ServiceLocator.getHotelrpcservice().getHotel(new DefaultWaitCallback<HotelDTO>() {
			@Override
			public void success(HotelDTO result) {
				ClientContext.getInstance().setHotel(result);
				UtilClient.getInstance().getClientFactory().getEventBus().fireEvent(new UnitOfWorkSelectedEvent());
			}
		});
	}

	private void initModule() {
		UtilClient utilClient = UtilClient.getInstance();
		utilClient.instancePresenters();
		utilClient.instanceInitialize();
		SimplePanel content = new SimplePanel();
		RootPanel.get("header").add(utilClient.getClientFactory().getHeaderUIView());
		RootPanel.get("menu").add(content);
		EventBus eventBus = utilClient.getClientFactory().getEventBus();
		PlaceController placeController = utilClient.getClientFactory().getPlaceController();
		ActivityMapper activityMapper = new AppActivityMapper();
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);
		activityManager.setDisplay(content);

		AppPlaceHistoryMapper historyMapper = GWT
				.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(placeController, eventBus, homePlace);
		historyHandler.handleCurrentHistory();
	}
}
