package ar.com.freesalesview.client.dtos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RoomDTO implements IsSerializable{
	
	private String subcategory;
	
	private Long idSubcategory;
	
	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public Long getIdSubcategory() {
		return idSubcategory;
	}

	public void setIdSubcategory(Long idSubcategory) {
		this.idSubcategory = idSubcategory;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof RoomDTO)) {
			return false;
		}
		RoomDTO other = (RoomDTO)obj;
		if (this.getIdSubcategory().equals(other.getIdSubcategory())){
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.subcategory.substring(0, 1).toUpperCase() + this.subcategory.substring(1).toLowerCase();
	}
}
