package ar.com.freesales.dto.json;


public class SplendiaChangeStockPostDTO {
	private String iNumRooms;
	private String iRoomStatus;
	private String iOldNumRooms;
	private String iOldRoomStatus;
	
	public SplendiaChangeStockPostDTO(String iNumRooms, String iRoomStatus, String iOldNumRooms, String iOldRoomStatus) {
		this.iNumRooms = iNumRooms;
		this.iRoomStatus = iRoomStatus;
		this.iOldNumRooms = iOldNumRooms;
		this.iOldRoomStatus = iOldRoomStatus;
	}
	
	public String getiNumRooms() {
		return iNumRooms;
	}
	public void setiNumRooms(String iNumRooms) {
		this.iNumRooms = iNumRooms;
	}
	public String getiRoomStatus() {
		return iRoomStatus;
	}
	public void setiRoomStatus(String iRoomStatus) {
		this.iRoomStatus = iRoomStatus;
	}
	public String getiOldNumRooms() {
		return iOldNumRooms;
	}
	public void setiOldNumRooms(String iOldNumRooms) {
		this.iOldNumRooms = iOldNumRooms;
	}
	public String getiOldRoomStatus() {
		return iOldRoomStatus;
	}
	public void setiOldRoomStatus(String iOldRoomStatus) {
		this.iOldRoomStatus = iOldRoomStatus;
	}
	
}
