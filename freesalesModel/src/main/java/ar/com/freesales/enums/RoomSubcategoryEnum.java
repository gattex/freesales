package ar.com.freesales.enums;

public enum RoomSubcategoryEnum {
	STANDARD_RATE("Standard Rate"),
	LAST_MINUTE("Last Minute"),
	NON_REFUNDABLE_RATE("Non Refundable Rate"),
	THREE_NIGHTS_STAY_MINIMUM("Tree Nights Stay Minimum");
	
	private String descripcion;
	
	RoomSubcategoryEnum(String descripcion){
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
}
