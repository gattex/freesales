package ar.com.freesalesview.client.presenters.header;

import ar.com.freesalesview.client.dtos.user.UserDTO;
import ar.com.freesalesview.client.utils.ClientContext;
import ar.com.freesalesview.client.utils.UtilClient;
import ar.com.freesalesview.client.views.header.HeaderUIView;

import com.google.gwt.place.shared.Place;

public class HeaderPresenter {
	
	public HeaderPresenter() {
		UtilClient instance = UtilClient.getInstance();
		HeaderUIView headerUIView = instance.getClientFactory().getHeaderUIView();
		headerUIView.setPresenter(this);
		UserDTO user = ClientContext.getInstance().getUser();
		headerUIView.setUserLogged(user.getFullName());
	}
	
	public void goTo(Place place) {
		UtilClient instance = UtilClient.getInstance();
		instance.getClientFactory().getPlaceController().goTo(place);
	}

}
