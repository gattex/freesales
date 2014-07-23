package ar.com.freesales.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ar.com.freesales.enums.RoomSubcategoryEnum;


public class RoomSubcategoryDTO {
	private RoomSubcategoryEnum roomSubcategoryEnum;
	private Long idRoomType;
	
	public Long getIdRoomType() {
		return idRoomType;
	}

	public RoomSubcategoryEnum getRoomSubcategoryEnum() {
		return roomSubcategoryEnum;
	}
	
	public void setIdRoomType(Long idRoomType) {
		this.idRoomType = idRoomType;
	}
	
	public void setRoomSubcategoryEnum(RoomSubcategoryEnum roomSubcategoryEnum) {
		this.roomSubcategoryEnum = roomSubcategoryEnum;
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