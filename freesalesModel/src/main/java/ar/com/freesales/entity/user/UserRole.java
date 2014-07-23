package ar.com.freesales.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLE")
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private UserDetail userDetail;
	private String role;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	

	@ManyToOne
	@JoinColumn(name = "ID_USER_DETAIL")
	public UserDetail getUserDetail() {
		return userDetail;
	}

	@Column(name = "ROLE")
	public String getRole() {
		return role;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}


	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}


	public void setRole(String role) {
		this.role = role;
	}

	
	
}
