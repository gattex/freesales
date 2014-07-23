package ar.com.freesalesview.client.presenters.dashboard;

import java.util.List;

import ar.com.freesalesview.client.dtos.UnitOfWorkDTO;
import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO.ChangeStateDTO;
import ar.com.freesalesview.client.enums.ChangeStatusEnum;
import ar.com.freesalesview.client.events.ChangeStatusEndEvent;
import ar.com.freesalesview.client.events.UnitOfWorkSelectedEvent;
import ar.com.freesalesview.client.factories.ClientFactory;
import ar.com.freesalesview.client.handlers.UnitOfWorkSelectedHandler;
import ar.com.freesalesview.client.services.utils.DefaultWaitCallback;
import ar.com.freesalesview.client.services.utils.ServiceLocator;
import ar.com.freesalesview.client.utils.ClientContext;
import ar.com.freesalesview.client.utils.UtilClient;
import ar.com.freesalesview.client.views.dashboard.unitOfWork.DashboardUnitOfWorkMainContentUIView;

import com.google.gwt.place.shared.Place;

public class DashboardMainContentPresenter implements UnitOfWorkSelectedHandler{
	
	private DashboardUnitOfWorkMainContentUIView dashboardMainContentUIView;
	private final UtilClient instance = UtilClient.getInstance();
	private final ClientFactory clientFactory = instance.getClientFactory(); 
	
	public DashboardMainContentPresenter() {
		dashboardMainContentUIView = clientFactory.getDashboardMainContentUIView();
		dashboardMainContentUIView.setPresenter(this);
		clientFactory.getEventBus().addHandler(UnitOfWorkSelectedEvent.TYPE, this);
	}
	
	public void goTo(Place place) {
		UtilClient instance = UtilClient.getInstance();
		instance.getClientFactory().getPlaceController().goTo(place);
	}

	@Override
	public void onUnitOfWorkSelected() {
		//Llamar al servicio para que obtener los unitofworksDtos
		//Invocar a la vista para que los llene
		//Podria poner un loading solo aca
		ServiceLocator.getNotificationrpcservice().getLastUnitsOfWorks(
				ClientContext.getInstance().getHotel().getId(),
				new DefaultWaitCallback<List<UnitOfWorkDTO>>() {
					@Override
					public void success(List<UnitOfWorkDTO> units) {
						dashboardMainContentUIView.fillNotifications(units);
					}
				});
	}
	
	
	public void checkChangeStatus(ChangeStateDTO dto) {
		ServiceLocator.getNotificationrpcservice().checkStatus(
				ClientContext.getInstance().getHotel().getId(), dto,
				new DefaultWaitCallback<ChangeStateDTO>() {
					@Override
					public void success(ChangeStateDTO result) {
						if (ChangeStatusEnum.SUCCESS.equals(result
								.getChangeStatusEnum())
								|| ChangeStatusEnum.FAIL.equals(result
										.getChangeStatusEnum())) {
							UtilClient
									.getInstance()
									.getClientFactory()
									.getEventBus()
									.fireEvent(new ChangeStatusEndEvent(result));
						}
					}
				});
	}

}
