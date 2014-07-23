package ar.com.freesales.entity.room;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.com.freesales.entity.hotel.HotelOta;
import ar.com.freesales.enums.ActionEnum;

@Entity
@Table(name = "HOTEL_OTA_ROOM_TYPE")
public class HotelOtaRoomType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private HotelOta hotelOta;
	private RoomType roomType;
	private String roomTypeCode;
	private BookingAdditionalInfo bookingAdditionalInfo;
	private boolean supportChangeStock; 
	private boolean supportChangeRate; 
	private boolean supportChangeAvailability; 
	
	@Id
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "ID_HOTEL_OTA")
	public HotelOta gethotelOta() {
		return hotelOta;
	}
	
	@ManyToOne
    @JoinColumn(name = "ID_ROOM_TYPE")
	public RoomType getRoomType() {
		return roomType;
	}
	
	@Column(name = "CODE")
	public String getRoomTypeCode() {
		return roomTypeCode;
	}
	
	@OneToOne(mappedBy = "hotelOtaRoomType")	
	public BookingAdditionalInfo getBookingAdditionalInfo() {
		return bookingAdditionalInfo;
	}

	@Column(name = "SUPPORT_CHANGE_STOCK", nullable = false, columnDefinition = "TINYINT(1)")
	public boolean isSupportChangeStock() {
		return supportChangeStock;
	}

	@Column(name = "SUPPORT_CHANGE_RATE", nullable = false, columnDefinition = "TINYINT(1)")
	public boolean isSupportChangeRate() {
		return supportChangeRate;
	}

	@Column(name = "SUPPORT_CHANGE_AVAILABILITY", nullable = false, columnDefinition = "TINYINT(1)")
	public boolean isSupportChangeAvailability() {
		return supportChangeAvailability;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHotelOta(HotelOta hotelOta) {
		this.hotelOta = hotelOta;
	}
	
	public void setRoomTypeCode(String roomTypeCode) {
		this.roomTypeCode = roomTypeCode;
	}
	
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setBookingAdditionalInfo(BookingAdditionalInfo bookingAdditionalInfo) {
		this.bookingAdditionalInfo = bookingAdditionalInfo;
	}

	public void setSupportChangeStock(boolean supportChangeStock) {
		this.supportChangeStock = supportChangeStock;
	}

	public void setSupportChangeRate(boolean supportChangeRate) {
		this.supportChangeRate = supportChangeRate;
	}

	public void setSupportChangeAvailability(boolean supportChangeAvailability) {
		this.supportChangeAvailability = supportChangeAvailability;
	}

	public boolean applyAction(ActionEnum actionEnum) {
		boolean apply = false;
		switch (actionEnum) {
		case CHANGE_AVAILABILITY:
			apply = isSupportChangeAvailability();
			break;
		case CHANGE_RATE:
			apply = isSupportChangeRate();
			break;
		case CHANGE_STOCK:
			apply = isSupportChangeStock();
			break;
		}
		return apply;
	}
	
	//este método es malisimo...ver q onda cuando haya varios additionaInfo
	public boolean hasAdditionalInfo(){
		return bookingAdditionalInfo != null;
	}
	
	
}
