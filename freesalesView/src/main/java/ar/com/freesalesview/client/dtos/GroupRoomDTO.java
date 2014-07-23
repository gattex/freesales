package ar.com.freesalesview.client.dtos;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupRoomDTO implements IsSerializable{
	
	private Long idRoomType;
	
	private String category;
	
	private List<RoomDTO> subcategories;
	
	public Long getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(Long idRoomType) {
		this.idRoomType = idRoomType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<RoomDTO> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<RoomDTO> subcategories) {
		this.subcategories = subcategories;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof GroupRoomDTO)) {
			return false;
		}
		GroupRoomDTO other = (GroupRoomDTO)obj;
		if (this.getIdRoomType().equals(other.getIdRoomType())){
			return true;
		}
		return false;

	}
	
	@Override
	public String toString() {
		return this.category.substring(0, 1).toUpperCase() + this.category.substring(1).toLowerCase();
	}
}
