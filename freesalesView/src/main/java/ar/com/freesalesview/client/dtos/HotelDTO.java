package ar.com.freesalesview.client.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.freesalesview.client.enums.ActionEnum;

import com.google.gwt.user.client.rpc.IsSerializable;

public class HotelDTO implements IsSerializable{
	
	private Long id;
	
	private String name;
	
	private Map<ActionEnum,List<OtaDTO>> actionOta;
	
	private Map<ActionEnum,List<GroupRoomDTO>> actionRoom;
	
	public HotelDTO() {
		this.actionOta = new HashMap<ActionEnum,List<OtaDTO>>();
		this.actionRoom = new HashMap<ActionEnum,List<GroupRoomDTO>>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public List<OtaDTO> getOtasByAction(ActionEnum action){
		return this.actionOta.get(action);
	}
	
	public List<GroupRoomDTO> getRoomByAction(ActionEnum action){
		return this.actionRoom.get(action);
	}
	
	public void setOtasByAction(List<OtaDTO> newOtas, ActionEnum action){
		List<OtaDTO> otas = this.actionOta.get(action);
		if (otas == null){
			otas = new ArrayList<OtaDTO>();
		}
		otas.addAll(newOtas);
		this.actionOta.put(action, otas);
	}
	
	public void setRoomsByAction(List<GroupRoomDTO> newOtas, ActionEnum action){
		List<GroupRoomDTO> otas = this.actionRoom.get(action);
		if (otas == null){
			otas = new ArrayList<GroupRoomDTO>();
		}
		otas.addAll(newOtas);
		this.actionRoom.put(action, otas);
	}
}
