package ar.com.freesales.entity.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.freesales.entity.hotel.Hotel;

@Entity
@Table(name = "USER_DETAIL")
public class UserDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Hotel hotel;
	private Set<UserRole> roles;
	private String userName;
	private String userPass;
	private String name;
	private String lastName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	

	@ManyToOne
	@JoinColumn(name = "ID_HOTEL")
	public Hotel getHotel() {
		return hotel;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}
	
	@Column(name = "USER_PASS")
	public String getUserPass() {
		return userPass;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	
	@OneToMany(mappedBy="userDetail")
	public Set<UserRole> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
}
