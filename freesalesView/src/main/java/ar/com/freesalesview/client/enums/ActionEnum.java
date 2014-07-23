package ar.com.freesalesview.client.enums;

public enum ActionEnum {
	
	RATE_UPDATE("updated the rate"),AVAILABILITY("updated the availability"), OPEN_CLOSE("updated open/close");

	private String value;
	
	private ActionEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
