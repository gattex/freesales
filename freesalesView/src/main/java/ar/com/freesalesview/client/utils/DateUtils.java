package ar.com.freesalesview.client.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateUtils {

	private static String FORMAT_DATE = "yyyyMMdd";
	private static String SHOW_FORMAT_DATE = "MMM dd";
	private static String SHOW_FORMAT_TIME = "'on' MMM d 'at' HH:mm aaa";
	
	private static long DATE = 24*60*60*1000;
	
	private static DateTimeFormat simpleDateFormat = DateTimeFormat.getFormat(FORMAT_DATE);
	private static DateTimeFormat simpleShowDateFormat = DateTimeFormat.getFormat(SHOW_FORMAT_DATE);
	private static DateTimeFormat simpleShowTimeFormat = DateTimeFormat.getFormat(SHOW_FORMAT_TIME);
	
	private static Date toDate (String value){
		return simpleDateFormat.parse(value);
	}
	
	public static List<Date> toDate (String... value){
		List<Date> dates = new ArrayList<Date>();
		for (String date : value) {
			dates.add(toDate(date));
		}
		return dates;
	}
	
	private static  String getDayOfMonthSuffix(final int n) {
	    if (n >= 11 && n <= 13) {
	        return "th";
	    }
	    switch (n % 10) {
	        case 1:  return "st";
	        case 2:  return "nd";
	        case 3:  return "rd";
	        default: return "th";
	    }
	}
	
	public static List<List<Date>> transformSelectedDates(List<Date> selectedDates){
		Iterator<Date> iterator = selectedDates.iterator();
		Date actual = null;
		Date nextDate = null;
		
		long actualDay, nextDay;
		List<List<Date>> result = new ArrayList<List<Date>>();
		Date initDate = iterator.next();
		List<Date> agroup = null;
		if (!iterator.hasNext()){
			agroup = new ArrayList<Date>();
			agroup.add(initDate);
			result.add(agroup);
		}
		for (; iterator.hasNext();) {
			
			actual = new Date(initDate.getTime());
			actualDay = actual.getTime();
			
			nextDate = new Date(iterator.hasNext() ? iterator.next().getTime(): null);
			nextDay = nextDate.getTime();
			if (actualDay+DATE == nextDay){ //Si la fecha contigua es el dia siguiente, lo agrupo
				if (agroup == null){
					agroup = new ArrayList<Date>();
					result.add(agroup);
				}
				if (!agroup.contains(actual)){
					agroup.add(actual);
				}
				if (!agroup.contains(nextDate)){
					agroup.add(nextDate);
				}
				initDate = nextDate;
			}else{ //Difiere en mas de un día
				if (agroup == null || (agroup!=null && !agroup.contains(actual))){
					agroup = new ArrayList<Date>();
					agroup.add(actual);
					result.add(agroup);
				}
				initDate = nextDate;
				agroup = null;
			}
		}
		if (agroup == null || (agroup!=null && nextDate != null && !agroup.contains(nextDate))){
			agroup = new ArrayList<Date>();
			agroup.add(nextDate);
			result.add(agroup);
		}
		return result;
	}
	
	
	public static String showStringBetweenDates (Date first, Date last){
		return ("From " + simpleShowDateFormat.format(first) +getDayOfMonthSuffix(first.getMonth()) + " to the " + simpleShowDateFormat.format(last) + getDayOfMonthSuffix(last.getMonth()));
	}
	
	public static String showStringDate (Date date){
		return (simpleShowDateFormat.format(date)+getDayOfMonthSuffix(date.getMonth()));
	}
	
	public static String showStringTime (Date date){
		return (simpleShowTimeFormat.format(date));
	}
}
