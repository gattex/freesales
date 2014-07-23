package ar.com.freesales.entity.changelog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "DATE_BATCH")
public class DateBatch {
	
	private Long id;
	private Long idDateBatch;
	private Date date;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	@Column(name = "ID_BATCH")
	public Long getIdDateBatch() {
		return idDateBatch;
	}
	
	@Column(name = "DATE")
	public Date getDate() {
		return date;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setIdDateBatch(Long idDateBatch) {
		this.idDateBatch = idDateBatch;
	}
	
//	business method
	@Transient
	public String getYearAsString(){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return String.valueOf(calendar.get(Calendar.YEAR));
	}
	
	@Transient
	public String getMonthAsString(){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		//le sumo uno porque enero arranca de 0
		return String.valueOf(1 + calendar.get(Calendar.MONTH));
	}
	
	@Transient
	public String getMonthAsStringTwoDigits(){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		//le sumo uno porque enero arranca de 0
		String asString = String.valueOf(1 + calendar.get(Calendar.MONTH));
		return asString.length() == 1 ? "0" + asString : asString;
	}
	
	@Transient
	public String getDayAsString(){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return String.valueOf(calendar.get(Calendar.DATE));
	}
	
	@Transient
	public String getDayAsStringTwoDigits(){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		String asString = String.valueOf(calendar.get(Calendar.DATE));
		return asString.length() == 1 ? "0" + asString : asString;
	}	
}
