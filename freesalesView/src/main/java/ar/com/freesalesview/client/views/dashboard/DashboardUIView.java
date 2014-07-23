package ar.com.freesalesview.client.views.dashboard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DashboardUIView extends Composite implements DashboardView{

	private static DashboardUIViewUiBinder uiBinder = GWT
			.create(DashboardUIViewUiBinder.class);

	interface DashboardUIViewUiBinder extends UiBinder<Widget, DashboardUIView> {
	}
	
	private Presenter presenter;
	
	@UiField
	SimplePanel dashboardMainContentPanel;
	
	@UiField
	SimplePanel dashboardNotificationPanel;

	public DashboardUIView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public SimplePanel getNotificationPanel() {
		return dashboardNotificationPanel;
	}
	
	@Override
	public SimplePanel getMainPanel() {
		return dashboardMainContentPanel;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
}
