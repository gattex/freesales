package ar.com.freesalesview.client.views.settings;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface SettingsView extends IsWidget {

	void setPresenter(Presenter presenter);

	public interface Presenter {
		void goTo(Place place);
	}
}
