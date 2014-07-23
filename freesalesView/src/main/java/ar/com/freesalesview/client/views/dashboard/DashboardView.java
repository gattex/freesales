package ar.com.freesalesview.client.views.dashboard;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public interface DashboardView extends IsWidget {

	void setPresenter(Presenter presenter);
	
	SimplePanel getMainPanel();
	
	SimplePanel getNotificationPanel();

	public interface Presenter {
		void goTo(Place place);
	}
}
