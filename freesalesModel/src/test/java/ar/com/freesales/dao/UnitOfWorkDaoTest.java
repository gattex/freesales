package ar.com.freesales.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ar.com.freesales.entity.changelog.UnitOfWork;

@ContextConfiguration(locations={"/ar/com/freesales/freesalesApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager="freesalesTransactionManager")
public class UnitOfWorkDaoTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private UnitOfWorkDao unitOfWorkDao;
	
	@Test
	public void testGetAllByHotel() {
		List<UnitOfWork> units = this.unitOfWorkDao.getLastUnitOfWorkByHotel(1L);
		assertNotNull(units);
	}

}
