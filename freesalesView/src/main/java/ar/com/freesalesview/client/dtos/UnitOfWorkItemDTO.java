package ar.com.freesalesview.client.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.freesalesview.client.enums.ChangeStatusEnum;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UnitOfWorkItemDTO implements IsSerializable{
	
	private String otaName;
	
	private Boolean isGroupable;
	
	private String roomCategory;
	
	private String roomSubcategory;
	
	private List<ChangeStateDTO> changesStates;

	public String getOtaName() {
		return otaName;
	}

	public void setOtaName(String otaName) {
		this.otaName = otaName;
	}

	public String getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}

	public String getRoomSubcategory() {
		return roomSubcategory;
	}

	public void setRoomSubcategory(String roomSubcategory) {
		this.roomSubcategory = roomSubcategory;
	}
	
	public List<ChangeStateDTO> getChangesStates() {
		return changesStates;
	}
	
	public void setChangesStates(List<ChangeStateDTO> changesStates) {
		this.changesStates = changesStates;
	}
	
	public void addChangeState(ChangeStateDTO changeState){
		if (this.changesStates == null){
			this.changesStates = new ArrayList<ChangeStateDTO>();
		}
		this.changesStates.add(changeState);
	}
	
	public void setIsGroupable(Boolean isGroupable) {
		this.isGroupable = isGroupable;
	}
	
	public Boolean isGroupable(){
		return this.isGroupable;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof UnitOfWorkItemDTO)){
			return false;
		}
		
		UnitOfWorkItemDTO another = (UnitOfWorkItemDTO)obj;
		if (this.getOtaName().equals(another.getOtaName()) && this.getRoomCategory().equals(another.getRoomCategory()) && this.getRoomSubcategory().equals(another.getRoomSubcategory())){
			return true;
		}
		return false;
	}
	
	public String getRoomToShow(){
		StringBuffer toShow = new StringBuffer();
		if (roomCategory != null){
			toShow.append(roomCategory.toLowerCase());
			toShow.append(" ");
		}
		if (this.roomSubcategory != null){
			toShow.append(roomSubcategory.toLowerCase());
		}
		return toShow.toString();
	}

	public static class ChangeStateDTO implements IsSerializable{
		private List<Date> dates;
		private ChangeStatusEnum changeStatusEnum;
		private Long idChangeLog;

		public ChangeStateDTO() {
		}

		public List<Date> getDates() {
			return dates;
		}

		public void setDates(List<Date> dates) {
			this.dates = dates;
		}

		public ChangeStatusEnum getChangeStatusEnum() {
			return changeStatusEnum;
		}

		public void setChangeStatusEnum(ChangeStatusEnum changeStatusEnum) {
			this.changeStatusEnum = changeStatusEnum;
		}
		
		public void setIdChangeLog(Long idChangeLog) {
			this.idChangeLog = idChangeLog;
		}
		
		public Long getIdChangeLog() {
			return idChangeLog;
		}
	}
}
