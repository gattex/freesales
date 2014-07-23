package ar.com.freesalesview.client.factories;

import ar.com.freesalesview.client.presenters.dashboard.DashboardMainContentPresenter;
import ar.com.freesalesview.client.presenters.dashboard.DashboardNotificationPresenter;
import ar.com.freesalesview.client.presenters.header.HeaderPresenter;

public class PresenterFactory {

	private static PresenterFactory presenterFactory;
	
	private HeaderPresenter presenterHeader;
	
	private DashboardMainContentPresenter dashboardMainContentPresenter;
	
	private DashboardNotificationPresenter dashboardNotificationPresenter;
	
	private PresenterFactory() {
		presenterHeader = new HeaderPresenter();
		dashboardMainContentPresenter = new DashboardMainContentPresenter();
		dashboardNotificationPresenter = new DashboardNotificationPresenter();
	}
	
	public static PresenterFactory getInstance(){
		if (presenterFactory == null){
			presenterFactory = new PresenterFactory();
		}
		return presenterFactory;
	}
	
	public HeaderPresenter getPresenterHeader() {
		return presenterHeader;
	}
	
	public DashboardMainContentPresenter getDashboardMainContentPresenter() {
		return dashboardMainContentPresenter;
	}
	
	public DashboardNotificationPresenter getDashboardNotificationPresenter() {
		return dashboardNotificationPresenter;
	}
}
