package ar.com.freesales.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ar.com.freesales.entity.changelog.ChangeLog;
import ar.com.freesales.entity.changelog.DateBatch;

@ContextConfiguration(locations={"/ar/com/freesales/freesalesApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager="freesalesTransactionManager")
public class ChangeLogDaoTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Resource(name="changeLogDao")
	private ChangeLogDao changeLogDao;
	
	@Test
	public void testGetDatesByBatch() {
		List<DateBatch> dateBatchs = changeLogDao.getDatesByBatch(17l);
		for (DateBatch dateBatch : dateBatchs) {
			System.out.println(dateBatch.getDate());
		}
	}
	
	@Test
	public void testGetChangeLogByBatch() {
//		List<ChangeLog> dateBatchs = changeLogDao.getChangeLogByBatch(17l);
//		for (ChangeLog changeLog : dateBatchs) {
//			System.out.println(changeLog.getStatusEnum());
//		}
	}

}
