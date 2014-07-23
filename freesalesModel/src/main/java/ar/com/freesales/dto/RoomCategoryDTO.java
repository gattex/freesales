package ar.com.freesales.dto;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ar.com.freesales.enums.RoomCategoryEnum;

public class RoomCategoryDTO {
	
	private RoomCategoryEnum roomCategoryEnum;
	private Long idRoomType;
	private Set<RoomSubcategoryDTO> roomSubcategoryDTOs = new HashSet<RoomSubcategoryDTO>();

	public Long getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(Long idRoomType) {
		this.idRoomType = idRoomType;
	}

	public RoomCategoryEnum getRoomCategoryEnum() {
		return roomCategoryEnum;
	}
	
	public void addRoomSubcategoryDTO(RoomSubcategoryDTO roomSubcategoryDTO){
		roomSubcategoryDTOs.add(roomSubcategoryDTO);
	}
	
	public void setRoomCategoryEnum(RoomCategoryEnum roomCategoryEnum) {
		this.roomCategoryEnum = roomCategoryEnum;
	}

	public Set<RoomSubcategoryDTO> getRoomSubcategoryDTOs() {
		return roomSubcategoryDTOs;
	}

	public void setRoomSubcategoryDTOs(
			Set<RoomSubcategoryDTO> roomSubcategoryDTOs) {
		this.roomSubcategoryDTOs = roomSubcategoryDTOs;
	}
	
	public void addAllRoomSubcategoryDTO(Set<RoomSubcategoryDTO> roomSubcategoryDTOs){
		for (RoomSubcategoryDTO roomSubcategoryDTO : roomSubcategoryDTOs) {
			addRoomSubcategoryDTO(roomSubcategoryDTO);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this,obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
