package ar.com.freesalesview.client.views.dashboard.unitOfWork;

import java.util.List;

import ar.com.freesalesview.client.dtos.UnitOfWorkDTO;
import ar.com.freesalesview.client.presenters.dashboard.DashboardMainContentPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DashboardUnitOfWorkMainContentUIView extends Composite{

	private static DashboardMainContentUIViewUiBinder uiBinder = GWT
			.create(DashboardMainContentUIViewUiBinder.class);

	interface DashboardMainContentUIViewUiBinder extends
			UiBinder<Widget, DashboardUnitOfWorkMainContentUIView> {
	}
	
	private DashboardMainContentPresenter presenter;
	
	@UiField
	VerticalPanel notifications;
	
	public DashboardUnitOfWorkMainContentUIView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setPresenter(DashboardMainContentPresenter presenter) {
		this.presenter = presenter;
	}
	
	public void fillNotifications(List<UnitOfWorkDTO> unitsOfWorks){
		notifications.clear();
		for (UnitOfWorkDTO unitOfWorkDTO : unitsOfWorks) {
			notifications.add(new DashboardUnitOfWorkNotificationUIView(unitOfWorkDTO,presenter));
		}
	}
}
