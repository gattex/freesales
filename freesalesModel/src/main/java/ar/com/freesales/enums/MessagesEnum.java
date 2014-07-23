package ar.com.freesales.enums;

public enum MessagesEnum {
	
	OTA_LOGIN_FAILED("login failed"),
	OTA_LOGIN_GENERAL_ERROR("general error"),
	OTA_LOGIN_PAGE_NOT_FOUND(" pagina no encontrada"),
	
	OTA_ACTION_CHANGE_RATE("general rate"),
	OTA_ACTION_CHANGE_STOCK("change stock rate"),
	OTA_ACTION_CHANGE_AVAILABILITY("change stock rate"),
	OTA_ACTION_GENERAL_ERROR("general error");
	
	private MessagesEnum(String v){
		this.value = v;
	}
	
	
	private String value;
	
	public String getValue() {
		return value;
	}
	

}
