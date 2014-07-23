package ar.com.freesales.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import ar.com.freesales.entity.changelog.DateBatch;

public class DateUtil {
	
	private DateTime firstDate;
	private DateTime lastDate;
	
	public DateUtil(List<DateBatch> dateBatchs) {
//		primero ordeno de la fecha más vieja a la mas nueva
		Collections.sort(dateBatchs, new Comparator<DateBatch>() {
			
			@Override
			public int compare(DateBatch o1, DateBatch o2) {
				// TODO Auto-generated method stub
				return o1.getDate().after(o2.getDate()) ? 1 : -1;
			}
		});
		firstDate = new DateTime(dateBatchs.get(0).getDate());
		lastDate = new DateTime(dateBatchs.get(dateBatchs.size() -1).getDate());
	}
	
	public int daysBetween(Date date){
		return Days.daysBetween(firstDate, new DateTime(date)).getDays();
	}
	
	public DateTime getFirstDate() {
		return firstDate;
	}
	
	public DateTime getLastDate() {
		return lastDate;
	}
	
	public String firstDayAsString(){
		return firstDate.dayOfMonth().getAsString();
	}
	
	public String firstYearAsString(){
		return firstDate.year().getAsString();
	}
	
	public String firstMonthAsString(){
		return firstDate.monthOfYear().getAsString();
	}
	
	public String firstMonthAsStringTwoDigit(){
		String asString = firstDate.monthOfYear().getAsString();
		return asString.length() == 1 ? "0"+ asString : asString;
	}
	
	public String firstDayAsStringTwoDigit(){
		String asString = firstDate.dayOfMonth().getAsString();
		return asString.length() == 1 ? "0"+ asString : asString;
	}
	
	public String firstDateAsString(){
		return firstYearAsString()  + "-" + firstMonthAsStringTwoDigit() + "-" + firstDayAsStringTwoDigit();
	}
	
	public String lastDayAsString(){
		return lastDate.dayOfMonth().getAsString();
	}
	
	public String lastYearAsString(){
		return lastDate.year().getAsString();
	}
	
	public String lastMonthAsString(){
		return lastDate.monthOfYear().getAsString();
	}
	
	public String lastMonthAsStringTwoDigit(){
		String asString = lastDate.monthOfYear().getAsString();
		return asString.length() == 1 ? "0"+ asString : asString;
	}
	
	public String lastDayAsStringTwoDigit(){
		String asString = lastDate.dayOfMonth().getAsString();
		return asString.length() == 1 ? "0"+ asString : asString;
	}

}
