package ar.com.freesales.entity.room;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.com.freesales.enums.RoomCategoryEnum;
import ar.com.freesales.enums.RoomSubcategoryEnum;

@Entity
@Table(name = "ROOM_TYPE")
public class RoomType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private RoomCategoryEnum roomCategoryEnum;
	private RoomSubcategoryEnum roomSubcategoryEnum;
	
	
	@Id
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	@Column(name = "ID_ROOM_CATEGORY")
	@Enumerated(EnumType.ORDINAL)
	public RoomCategoryEnum getRoomCategoryEnum() {
		return roomCategoryEnum;
	}

	@Column(name = "ID_ROOM_SUBCATEGORY")
	@Enumerated(EnumType.ORDINAL)
	public RoomSubcategoryEnum getRoomSubcategoryEnum() {
		return roomSubcategoryEnum;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setRoomCategoryEnum(RoomCategoryEnum roomCategoryEnum) {
		this.roomCategoryEnum = roomCategoryEnum;
	}
	
	public void setRoomSubcategoryEnum(RoomSubcategoryEnum roomSubcategoryEnum) {
		this.roomSubcategoryEnum = roomSubcategoryEnum;
	}
	
}
