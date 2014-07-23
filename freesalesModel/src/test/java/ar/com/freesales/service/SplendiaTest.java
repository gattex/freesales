package ar.com.freesales.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ar.com.freesales.dao.ChangeLogDao;
import ar.com.freesales.dao.GenericDao;
import ar.com.freesales.entity.ota.Ota;
import ar.com.freesales.entity.ota.Splendia;

@ContextConfiguration(locations={"/ar/com/freesales/freesalesApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager="freesalesTransactionManager")
public class SplendiaTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Resource(name="genericDao")
	private GenericDao genericDao;
	
	@Resource(name="changeLogDao")
	private ChangeLogDao changeLogDao;
	
	@Test
	public void testBuildChangeAvailabilityPost() {
//		Splendia splendia = (Splendia)genericDao.get(Ota.class, 1l);
//		List <NameValuePair> nvps2 = splendia.buildChangeAvailabilityPost(changeLogDao.getDatesByBatch(2l), 234324l, false);
//		for (NameValuePair nameValuePair : nvps2) {
//			System.out.println(nameValuePair.getName());
//			System.out.println(nameValuePair.getValue());
//		}
	}

	@Test
	public void testBuildChangeStockPost() {
//		Splendia splendia = (Splendia)genericDao.get(Ota.class, 1l);
//		List <NameValuePair> nvps2 = splendia.buildChangeStockPost(changeLogDao.getDatesByBatch(2l), 234324l,4);
//		for (NameValuePair nameValuePair : nvps2) {
//			System.out.println(nameValuePair.getName());
//			System.out.println(nameValuePair.getValue());
//		}
	}

}
