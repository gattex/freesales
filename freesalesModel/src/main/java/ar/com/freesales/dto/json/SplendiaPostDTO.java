package ar.com.freesales.dto.json;


public class SplendiaPostDTO {
	private transient String datePlusRoomTypeCode;
	
	public SplendiaPostDTO(String year, String month, String day, String roomTypeCode) {
//		ej: 2013-03-20_61491
		datePlusRoomTypeCode  = year + "-" + month + "-" +day + "_" + roomTypeCode;
	}
	
	public String getDatePlusRoomTypeCode() {
		return datePlusRoomTypeCode;
	}

	public void setDatePlusRoomTypeCode(String datePlusRoomTypeCode) {
		this.datePlusRoomTypeCode = datePlusRoomTypeCode;
	}

}
