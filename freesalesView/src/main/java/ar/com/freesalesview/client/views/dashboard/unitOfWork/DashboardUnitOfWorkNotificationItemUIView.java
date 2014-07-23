package ar.com.freesalesview.client.views.dashboard.unitOfWork;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO;
import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO.ChangeStateDTO;
import ar.com.freesalesview.client.enums.ChangeStatusEnum;
import ar.com.freesalesview.client.events.ChangeStatusEndEvent;
import ar.com.freesalesview.client.handlers.ChangeStatusEndHandler;
import ar.com.freesalesview.client.presenters.dashboard.DashboardMainContentPresenter;
import ar.com.freesalesview.client.utils.DateUtils;
import ar.com.freesalesview.client.utils.UtilClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DashboardUnitOfWorkNotificationItemUIView extends Composite  implements ChangeStatusEndHandler  {

	private static DashboardUnitOfWorkNotificationItemUIViewUiBinder uiBinder = GWT
			.create(DashboardUnitOfWorkNotificationItemUIViewUiBinder.class);

	interface DashboardUnitOfWorkNotificationItemUIViewUiBinder extends
			UiBinder<Widget, DashboardUnitOfWorkNotificationItemUIView> {
	}
	
	@UiField
	Label otaName;

	@UiField
	Label roomName;
	
	@UiField
	SpanElement dates;
	
	@UiField
	VerticalPanel changeStatusPanel;
	
	private Map<Long,Timer> timers;
	
	private String waitStatusClassName = "icon-info-sign waitUOFW";
	private String errorStatusClassName = "icon-remove-sign errorUOFW";
	private String okStatusClassName = "icon-ok-sign okUOFW";
	
	private DashboardMainContentPresenter presenter;
	
	public DashboardUnitOfWorkNotificationItemUIView(UnitOfWorkItemDTO item, DashboardMainContentPresenter presenter) {
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));
		UtilClient.getInstance().getClientFactory().getEventBus().addHandler(ChangeStatusEndEvent.TYPE, this);
		this.timers = new HashMap<Long, Timer>();
		draw(item);
	}

	private void draw(UnitOfWorkItemDTO item) {
		this.otaName.setText(item.getOtaName());
		this.roomName.setText(item.getRoomToShow());
		StringBuffer buffer = new StringBuffer();
		String className=null;
		final List<ChangeStateDTO> statusPing = new ArrayList<ChangeStateDTO>();
		
		TreeMap<Date,StatusChangLogWidget> allDates = new TreeMap<Date,StatusChangLogWidget>();

		for (ChangeStateDTO changeStateDTO : item.getChangesStates()) {
			if (ChangeStatusEnum.INIT.equals(changeStateDTO.getChangeStatusEnum())){
				statusPing.add(changeStateDTO);
				className = waitStatusClassName;
			}else if (ChangeStatusEnum.SUCCESS.equals(changeStateDTO.getChangeStatusEnum())){
				className = okStatusClassName;
			}else if (ChangeStatusEnum.FAIL.equals(changeStateDTO.getChangeStatusEnum())){
				className = errorStatusClassName;
			}
			
			if (item.isGroupable()){
				for (String date : get(changeStateDTO.getDates())) {
					StatusChangLogWidget s = new StatusChangLogWidget(changeStateDTO.getIdChangeLog());
					s.addStyleName(className);
					buffer.append(date).append("  ").append("<br/>");
					this.changeStatusPanel.add(s);
					addStyleChangeStatusPanel();
				}
			}else{
				StatusChangLogWidget s = new StatusChangLogWidget(changeStateDTO.getIdChangeLog());
				s.addStyleName(className);
				for (Date d : changeStateDTO.getDates()) {
					allDates.put(d, s);
				}
			}
		}
		if (!item.isGroupable()){
			for (Date date : allDates.keySet()) {
				buffer.append(DateUtils.showStringDate(date)).append("  ").append("<br/>");
				this.changeStatusPanel.add(allDates.get(date));
				addStyleChangeStatusPanel();
			}
		}
		
		this.dates.setInnerHTML(buffer.toString());
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (statusPing.size() > 0){
					for (ChangeStateDTO changeStateDTO : statusPing) {
						checkChangeStatus(changeStateDTO);
					}
				}
			}
		});
	}
	
	private void checkChangeStatus(final ChangeStateDTO changeStateDTO){
		Timer t = new TimerCheckStatus(changeStateDTO);
		this.timers.put(changeStateDTO.getIdChangeLog(), t);
		t.scheduleRepeating(30000);
	}

	private void addStyleChangeStatusPanel() {
		int widgetCount = this.changeStatusPanel.getWidgetCount();
		this.changeStatusPanel.getWidget(widgetCount-1).getElement().getParentElement().getStyle().setProperty("borderTop", "0px");
		this.changeStatusPanel.getWidget(widgetCount-1).getElement().getParentElement().getStyle().setProperty("padding", "2px");
	}
	
	private List<String> get(List<Date> dates){
		List<List<Date>> groups = DateUtils.transformSelectedDates(dates);
		List<String> toShow = new ArrayList<String>();
		for (List<Date> group : groups) {
			if (group.size() > 1){
				toShow.add(DateUtils.showStringBetweenDates(group.get(0), group.get(group.size()-1)));
			}else{
				toShow.add(DateUtils.showStringDate(group.get(0)));
			}
		}
		return toShow;
	}
	
	@Override
	public void onCheckStatusEnd(ChangeStateDTO dto) {
		Timer timer = timers.get(dto.getIdChangeLog());
		if (timer != null){
			timer.cancel();
			StatusChangLogWidget widget = null;
			for (int i = 0; i < changeStatusPanel.getWidgetCount(); i++) {
				widget = (StatusChangLogWidget)changeStatusPanel.getWidget(i);
				if (widget.getIdChange().equals(dto.getIdChangeLog())){
					widget.removeStyleName(waitStatusClassName);
					if (ChangeStatusEnum.SUCCESS.equals(dto.getChangeStatusEnum())){
						widget.addStyleName(okStatusClassName);
					}else if (ChangeStatusEnum.FAIL.equals(dto.getChangeStatusEnum())){
						widget.addStyleName(errorStatusClassName);
					}
				}
			}
		}
	}

	
	private class StatusChangLogWidget extends HTMLPanel{
		
		private Long idChange;
		
		public StatusChangLogWidget(Long id) {
			super("");
			this.idChange = id;
		}
		
		public Long getIdChange() {
			return idChange;
		}
	}
	
	private class TimerCheckStatus  extends Timer{
		
		private ChangeStateDTO dto;
		
		public TimerCheckStatus(ChangeStateDTO dto) {
			this.dto = dto;
		}
		
		@Override
		public void run() {
			presenter.checkChangeStatus(dto);
		}
	}
}



