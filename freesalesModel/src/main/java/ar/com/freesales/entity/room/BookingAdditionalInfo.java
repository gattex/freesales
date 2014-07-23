package ar.com.freesales.entity.room;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "BOOKING_ADDITIONAL_INFO")
public class BookingAdditionalInfo implements Serializable{
	
	private Long id;
	private HotelOtaRoomType hotelOtaRoomType;
	private String numberOpenClose;
	private String codeOpenClose;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	@ManyToOne
    @JoinColumn(name = "ID_HOTEL_OTA_ROOM_TYPE")
	public HotelOtaRoomType getHotelOtaRoomType() {
		return hotelOtaRoomType;
	}
	
	@Column (name = "NUMBER_OPEN_CLOSE")
	public String getNumberOpenClose() {
		return numberOpenClose;
	}
	
	@Column (name = "CODE_OPEN_CLOSE")
	public String getCodeOpenClose() {
		return codeOpenClose;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setHotelOtaRoomType(HotelOtaRoomType hotelOtaRoomType) {
		this.hotelOtaRoomType = hotelOtaRoomType;
	}

	public void setNumberOpenClose(String numberOpenClose) {
		this.numberOpenClose = numberOpenClose;
	}

	public void setCodeOpenClose(String codeOpenClose) {
		this.codeOpenClose = codeOpenClose;
	}
	
	
}
