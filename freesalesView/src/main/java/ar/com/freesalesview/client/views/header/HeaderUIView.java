package ar.com.freesalesview.client.views.header;

import ar.com.freesalesview.client.places.AvailabilityPlace;
import ar.com.freesalesview.client.places.DashboardPlace;
import ar.com.freesalesview.client.places.OpenClosePlace;
import ar.com.freesalesview.client.places.RateUpdatePlace;
import ar.com.freesalesview.client.places.SettingsPlace;
import ar.com.freesalesview.client.presenters.header.HeaderPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class HeaderUIView extends Composite{

	private static HeaderUIViewUiBinder uiBinder = GWT
			.create(HeaderUIViewUiBinder.class);

	interface HeaderUIViewUiBinder extends UiBinder<Widget, HeaderUIView> {
	}
	
	private HeaderPresenter presenter;
	
	@UiField
	Anchor dashboardLink;
	
	@UiField
	Anchor rateUpdateLink;
	
	@UiField
	Anchor availabilityLink;
	
	@UiField
	Anchor openCloseLink;
	
	@UiField
	Anchor settingsLink;
	
	@UiField
	SpanElement userLogged;
	

	public HeaderUIView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler(value="dashboardLink")
	public void onClickDashboard(ClickEvent event){
		this.removeStyles();
		this.dashboardLink.addStyleName("linksNavBarClick");
		this.presenter.goTo(new DashboardPlace());
	}
	
	@UiHandler(value="rateUpdateLink")
	public void onClickRateUpdate(ClickEvent event){
		this.removeStyles();
		this.rateUpdateLink.addStyleName("linksNavBarClick");
		this.presenter.goTo(new RateUpdatePlace());
	}
	
	@UiHandler(value="availabilityLink")
	public void onClickAvailability(ClickEvent event){
		this.removeStyles();
		this.availabilityLink.addStyleName("linksNavBarClick");
		this.presenter.goTo(new AvailabilityPlace());

	}
	
	@UiHandler(value="openCloseLink")
	public void onClickOpenCloseLink(ClickEvent event){
		this.removeStyles();
		this.openCloseLink.addStyleName("linksNavBarClick");
		this.presenter.goTo(new OpenClosePlace());
	}
	
	@UiHandler(value="settingsLink")
	public void onClickSettingsLink(ClickEvent event){
		this.removeStyles();
		this.settingsLink.addStyleName("linksNavBarClick");
		this.presenter.goTo(new SettingsPlace());
	}
	
	public void setPresenter(HeaderPresenter presenterHeader) {
		this.presenter = presenterHeader;
	}
	
	public void setUserLogged(String name){
		this.userLogged.setInnerText(name);
	}
	
	private void removeStyles(){
		this.dashboardLink.removeStyleName("linksNavBarClick");
		this.rateUpdateLink.removeStyleName("linksNavBarClick");
		this.availabilityLink.removeStyleName("linksNavBarClick");
		this.openCloseLink.removeStyleName("linksNavBarClick");
		this.settingsLink.removeStyleName("linksNavBarClick");
	}
}

