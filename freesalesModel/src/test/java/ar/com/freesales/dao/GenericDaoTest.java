package ar.com.freesales.dao;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ar.com.freesales.entity.hotel.Hotel;
import ar.com.freesales.entity.hotel.HotelOta;
import ar.com.freesales.entity.ota.Ota;
import ar.com.freesales.entity.room.HotelOtaRoomType;
import ar.com.freesales.exception.OtaLoginException;

@ContextConfiguration(locations={"/ar/com/freesales/freesalesApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager="freesalesTransactionManager")
public class GenericDaoTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource(name="genericDao")
	private GenericDao genericDao;
	
	
	@Test
	public void testFindAll(){
		Hotel hotel = genericDao.get(Hotel.class,1L);
		assertNotNull(hotel);
		Set<HotelOta> hotelOtas = hotel.getHotelOtas();
		
		System.out.println("soy el hotel: " + hotel.getName());
		System.out.println(" mis otas son: ");
		for (HotelOta hotelOta : hotelOtas) {
			Ota ota = hotelOta.getOta();
			System.out.println(ota.getName());
			System.out.println("url de log: " + ota.getUrlLog());
			System.out.println("user: " + hotelOta.getUser());
			System.out.println("pass: " + hotelOta.getPass());
			
			System.out.println("codigos de habitación: ");
			
			Set<HotelOtaRoomType> codigosHabitaciones = hotelOta.getHotelOtaRoomTypes();
			
			for (HotelOtaRoomType hotelOtaRoomType : codigosHabitaciones) {
				System.out.println("para la habitación: " + hotelOtaRoomType.getRoomType().getRoomCategoryEnum() + hotelOtaRoomType.getRoomType().getRoomCategoryEnum() );
				System.out.println("codigo: " + hotelOtaRoomType.getRoomTypeCode());
			}
		}
	}
	
	@Test
	public void testLogin(){
		Hotel hotel = genericDao.get(Hotel.class,1L);
		assertNotNull(hotel);
		Set<HotelOta> hotelOtas = hotel.getHotelOtas();
		
		System.out.println("soy el hotel: " + hotel.getId());
		for (HotelOta hotelOta : hotelOtas) {
			String user = hotelOta.getUser();
			String pass = hotelOta.getPass();
			try {
				Ota ota = hotelOta.getOta();
				ota.login(user, pass, null);
			} catch (OtaLoginException e) {
				fail(e.getMessage());
			}
		}
	}
	
	@Test
	public void testLoginFail(){
		Hotel hotel = genericDao.get(Hotel.class,1L);
		assertNotNull(hotel);
		Set<HotelOta> hotelOtas = hotel.getHotelOtas();
		for (HotelOta hotelOta : hotelOtas) {
			String user = "myuser";
			String pass = "mypass";
			try {
				Ota ota = hotelOta.getOta();
				ota.login(user, pass, null);
				fail();
			} catch (OtaLoginException e) {
				assertTrue(e != null);
			}
		}
	}
	
}
