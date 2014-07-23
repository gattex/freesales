package ar.com.freesalesview.client.views.dashboard.unitOfWork;

import ar.com.freesalesview.client.dtos.UnitOfWorkDTO;
import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO;
import ar.com.freesalesview.client.presenters.dashboard.DashboardMainContentPresenter;
import ar.com.freesalesview.client.utils.DateUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DashboardUnitOfWorkNotificationUIView extends Composite{

	private static DashboardMainContentNotificationUIViewUiBinder uiBinder = GWT
			.create(DashboardMainContentNotificationUIViewUiBinder.class);

	interface DashboardMainContentNotificationUIViewUiBinder extends
			UiBinder<Widget, DashboardUnitOfWorkNotificationUIView> {
	}
	
	@UiField
	Label summary;
	
	@UiField
	Label dateAction;
	
	@UiField
	HTMLPanel items;
	
	private DashboardMainContentPresenter presenter;

	public DashboardUnitOfWorkNotificationUIView(UnitOfWorkDTO unitOfWorkDTO,DashboardMainContentPresenter presenter) {
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));
		draw(unitOfWorkDTO);
	}

	private void draw(UnitOfWorkDTO unitOfWorkDTO) {
		this.summary.setText(unitOfWorkDTO.getSummary());
		this.dateAction.setText(DateUtils.showStringTime(unitOfWorkDTO.getDateAction()));
		for (UnitOfWorkItemDTO item : unitOfWorkDTO.getItems()) {
			this.items.add(new DashboardUnitOfWorkNotificationItemUIView(item,presenter));
		}
	}
}
