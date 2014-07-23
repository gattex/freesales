package ar.com.freesalesview.client.utils;

import ar.com.freesalesview.client.dtos.HotelDTO;
import ar.com.freesalesview.client.dtos.user.UserDTO;

public class ClientContext {
	
	private static ClientContext instance;
	
	private UserDTO user;
	
	private HotelDTO hotel;
	
	private ClientContext() {}
	
	public static ClientContext getInstance() {
		if (instance == null){
			instance = new ClientContext();
		}
		return instance;
	}
	
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public UserDTO getUser() {
		return user;
	}
	
	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	
	public HotelDTO getHotel() {
		return hotel;
	}
}
