package ar.com.freesalesview.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils {

	private static String FORMAT_DATE = "yyyyMMdd";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
	
	private static Date toDate (String value){
		try {
			return simpleDateFormat.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	public static List<Date> toDate (String... value){
		List<Date> dates = new ArrayList<Date>();
		for (String date : value) {
			dates.add(toDate(date));
		}
		return dates;
	}
}
