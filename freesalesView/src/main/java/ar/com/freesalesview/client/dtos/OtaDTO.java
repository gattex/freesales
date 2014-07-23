package ar.com.freesalesview.client.dtos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OtaDTO implements IsSerializable{

	private Long id;
	
	private String name;
	
	private Long hotelOtaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getHotelOtaId() {
		return hotelOtaId;
	}
	
	public void setHotelOtaId(Long hotelOtaId) {
		this.hotelOtaId = hotelOtaId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OtaDTO other = (OtaDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.name.substring(0, 1).toUpperCase() + this.name.substring(1).toLowerCase();
	}
}
