package ar.com.freesales.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations={"/ar/com/freesales/freesalesApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager="freesalesTransactionManager")
public class ProcessBatchServiceTest extends AbstractTransactionalJUnit4SpringContextTests{ 
	
	@Autowired
	private ProcessBatchService batchService;
	
	@Test
	public void testProcessUnitOfWork(){
		batchService.processUnitOfWork(5L);
	}
	

}
