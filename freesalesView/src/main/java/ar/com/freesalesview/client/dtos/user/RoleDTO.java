package ar.com.freesalesview.client.dtos.user;

import java.io.Serializable;

public class RoleDTO implements Serializable{
	
	private static final long serialVersionUID = 7229246397054922216L;

	private String name;
	
	public RoleDTO(String name) {
		super();
		this.name = name;
	}
	
	public RoleDTO() {}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof RoleDTO)){
			return false;
		}
		RoleDTO other = (RoleDTO)obj;
		if (this.getName().equalsIgnoreCase(other.getName())){
			return true;
		}
		return false;
	}
}
