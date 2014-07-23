package ar.com.freesales.entity.hotel;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HOTEL")
public class Hotel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Set<HotelOta> hotelOtas;
	
	private String name;
	private String mail;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	

	@OneToMany(mappedBy = "hotel")
	public Set<HotelOta> getHotelOtas() {
		return hotelOtas;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	@Column(name = "MAIL")
	public String getMail() {
		return mail;
	}	
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setHotelOtas(Set<HotelOta> hotelOtas) {
		this.hotelOtas = hotelOtas;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
}
