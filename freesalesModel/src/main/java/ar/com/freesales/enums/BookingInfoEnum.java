package ar.com.freesales.enums;


public enum BookingInfoEnum {
	
	LOGIN_PARAM_SESSION("ses"),
	CHANGE_PARAM_OP("op"),
	CHANGE_PARAM_POST_ID("post_id"),
	HOTEL_ID("hotel_id"),
	CHANGE_PARAM_INIT_DAY("dfd"),
	CHANGE_PARAM_INIT_MONTH_AND_YEAR("dfym"),
	CHANGE_PARAM_END_DAY("dld"),
	CHANGE_PARAM_END_MONTH_AND_YEAR("dlym"),
	CHANGE_STOCK_OLD_VALUE("o_rir_"),
	CHANGE_STOCK_NEW_VALUE("rir_");
	
	private BookingInfoEnum(String value){
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}

}
