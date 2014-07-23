package ar.com.freesalesview.client.dtos;

import java.util.Date;
import java.util.List;

import ar.com.freesalesview.client.enums.ActionEnum;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UnitOfWorkDTO implements IsSerializable{
	
	private Long id;
	
	private String userName;
	
	private String lastName;
	
	private Double value;
	
	private ActionEnum actionEnum;
	
	private Date dateAction;
	
	private List<UnitOfWorkItemDTO> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public ActionEnum getActionEnum() {
		return actionEnum;
	}
	
	public void setActionEnum(ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}
	
	public List<UnitOfWorkItemDTO> getItems() {
		return items;
	}
	
	public void setItems(List<UnitOfWorkItemDTO> items) {
		this.items = items;
	}
	
	public Date getDateAction() {
		return dateAction;
	}
	
	public void setDateAction(Date dateAction) {
		this.dateAction = dateAction;
	}
	
//	John Doe updated the rate to $150.00 / night for 2 rooms...
	public String getSummary(){
		return this.getUserName() + " " +this.getLastName() + " " + this.actionEnum.getValue();
	}
}
