package ar.com.freesalesview.client.presenters.dashboard;

import ar.com.freesalesview.client.utils.UtilClient;
import ar.com.freesalesview.client.views.dashboard.notifications.DashboardNotificationUIView;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;

public class DashboardNotificationPresenter {
	
	public DashboardNotificationPresenter() {
		UtilClient instance = UtilClient.getInstance();
		DashboardNotificationUIView dashboardNotificationUIView = instance.getClientFactory().getDashboardNotificationUIView();
		dashboardNotificationUIView.setPresenter(this);
	}
	
	public void goTo(Place place) {
		UtilClient instance = UtilClient.getInstance();
		instance.getClientFactory().getPlaceController().goTo(place);
	}

	/**
	 * Busca las notificaciones para el hotel. Si las hay, crea objetos
	 * #DashboardNotificationSingleWidgetUIView y los guarda en el panel
	 * notificationsUl de la clase #DashboardNotificationUIView
	 * Una vez que se hayan cargado las notificaciones debe inicializar el timer para comprobar si hay nuevas.
	 */
	public void chargeNotification() {

	}


	/**
	 * Realiza un ping cada X segundos.
	 */
	private void initializeTimerCheckNotification() {
		Timer timer = new Timer() {
			public void run() {
				//TODO invocar un servicio que indica si tiene o no notificaciones para el hotel.
			}
		};
		timer.scheduleRepeating(2000);
	}

}
