package ar.com.freesales.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ar.com.freesales.dao.GenericDao;
import ar.com.freesales.entity.user.UserDetail;
import ar.com.freesales.enums.ActionEnum;

@ContextConfiguration(locations={"/ar/com/freesales/freesalesApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager="freesalesTransactionManager")
public class ProcessActionServiceTest extends AbstractTransactionalJUnit4SpringContextTests{ 
	
	@Autowired
	private ProcessActionService processActionService;
	
	@Autowired
	private GenericDao genericDao;

	@Test
	@Rollback(false)
	public void testDoActionSetOfDateSetOfLongSetOfLongUserDetailActionEnumInteger() {
		UserDetail userDetail = genericDao.get(UserDetail.class, 1l);
		Set<Long>idsHotelOtas = new HashSet<>();
		idsHotelOtas.add(3l); // Nuss - Booking
		
		Set<Long>idsRoomType = new HashSet<>();
		idsRoomType.add(9l); //deluxe - non refundable rate
		
		processActionService.doAction(buildDates(), idsRoomType, idsHotelOtas, userDetail, ActionEnum.CHANGE_RATE, 170);
	}

	
	private Set<Date> buildDates() {
		Set<Date> dates = new HashSet<Date>();
		int anio = 2013;
		
		Calendar date1 = GregorianCalendar.getInstance();
		date1.set(Calendar.YEAR, anio);
		date1.set(Calendar.MONTH, Calendar.SEPTEMBER);
		date1.set(Calendar.DAY_OF_MONTH, 3);
		
		
		
		Calendar date2 = (Calendar)date1.clone(); 
		date2.set(Calendar.YEAR, anio);
		date2.set(Calendar.MONTH, Calendar.SEPTEMBER);
		date2.set(Calendar.DAY_OF_MONTH, 4);
		
		
		Calendar date3 = (Calendar)date1.clone(); 
		date3.set(Calendar.YEAR, anio);
		date3.set(Calendar.MONTH, Calendar.SEPTEMBER);
		date3.set(Calendar.DAY_OF_MONTH, 8);
		
		
		dates.add(date1.getTime());
		dates.add(date2.getTime());
		dates.add(date3.getTime());
		
		return dates;
	}
}
