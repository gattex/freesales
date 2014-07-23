package ar.com.freesales.enums;

public enum SplendiaInfoEnum {

	LOGIN_WRONG_USER_PASS("Error: wrong username or password"),
	CHANGE_DATA_SAVED("Data saved successfully"),
	CHANGE_DATA_ERROR("Error saving data.Try it again in few minutes.");
	
	private SplendiaInfoEnum(String value){
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
}
