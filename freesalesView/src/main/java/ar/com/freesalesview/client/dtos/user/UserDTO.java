package ar.com.freesalesview.client.dtos.user;

import java.io.Serializable;
import java.util.Set;

public class UserDTO implements Serializable{
	
	private static final long serialVersionUID = -150360233339126130L;

	private String name;
	
	private String lastName;
	
	private Set<RoleDTO> roles;

	public UserDTO(String name, String lastName, Set<RoleDTO> roles) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.roles = roles;
	}
	
	public UserDTO() {}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<RoleDTO> getRoles() {
		return roles;
	}
	
	public String getFullName(){
		return this.name + " " + this.lastName;
	}
}
