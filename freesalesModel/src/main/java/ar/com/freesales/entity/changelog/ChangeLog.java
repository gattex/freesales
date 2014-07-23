package ar.com.freesales.entity.changelog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.freesales.entity.hotel.HotelOta;
import ar.com.freesales.entity.room.RoomType;
import ar.com.freesales.enums.StatusEnum;

@SuppressWarnings("serial")
@Entity
@Table(name = "CHANGE_LOG")
public class ChangeLog implements Serializable{
	
	private Long id;
	private HotelOta hotelOta;
	private RoomType roomType;
	private StatusEnum statusEnum;
	private Long idDateBatch;
	private String errorDetail;
	private UnitOfWork unitOfWork;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	@ManyToOne
    @JoinColumn(name = "ID_UNIT_OF_WORK")
	public UnitOfWork getUnitOfWork() {
		return unitOfWork;
	}
	
	@ManyToOne
    @JoinColumn(name = "ID_HOTEL_OTA")
	public HotelOta getHotelOta() {
		return hotelOta;
	}
	
	@Column (name = "ID_DATE_BATCH")
	public Long getIdDateBatch() {
		return idDateBatch;
	}
	
	@ManyToOne
    @JoinColumn(name = "ID_ROOM_TYPE")
	public RoomType getRoomType() {
		return roomType;
	}
	
	@Column(name = "ID_STATUS")
	@Enumerated(EnumType.ORDINAL)
	public StatusEnum getStatusEnum() {
		return statusEnum;
	}
	
	@Column(name = "ERROR_DETAIL")
	public String getErrorDetail() {
		return errorDetail;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHotelOta(HotelOta hotelOta) {
		this.hotelOta = hotelOta;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setStatusEnum(StatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}

	public void setIdDateBatch(Long idDateBatch) {
		this.idDateBatch = idDateBatch;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
	
	public void setUnitOfWork(UnitOfWork unitOfWork) {
		this.unitOfWork = unitOfWork;
	}
	
	//business
	@Transient
	public Integer getValue() {
		return unitOfWork.getValue();
	}
	
	@Transient
	public boolean isOpen() {
		return unitOfWork.getValue() == 1;
	}
	
	
	
}
