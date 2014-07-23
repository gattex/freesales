package ar.com.freesales.entity.changelog;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.freesales.entity.user.UserDetail;
import ar.com.freesales.enums.ActionEnum;

@SuppressWarnings("serial")
@Entity
@Table(name = "UNIT_OF_WORK")
public class UnitOfWork implements Serializable{
	
	private Long id;
	private UserDetail userDetail;
	private ActionEnum actionEnum;
	private Date date;
	private Integer value;
	

	private Boolean finish = Boolean.FALSE;
	private Set<ChangeLog> changeLogs;

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
	
	@Column (name = "ID_ACTION")
	@Enumerated(EnumType.ORDINAL)
	public ActionEnum getActionEnum() {
		return actionEnum;
	}
	
	@Column(name = "VALUE")
	public Integer getValue() {
		return value;
	}
	
	@Column(name = "DATE")
	public Date getDate() {
		return date;
	}
	
	@OneToMany(mappedBy = "unitOfWork")
	public Set<ChangeLog> getChangeLogs() {
		return changeLogs;
	}
	
	@Column(name = "FINISH")
	public Boolean getFinish() {
		return finish;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public void setActionEnum(ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setChangeLogs(Set<ChangeLog> changeLogs) {
		this.changeLogs = changeLogs;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public void setFinish(Boolean finish) {
		this.finish = finish;
	}
}
