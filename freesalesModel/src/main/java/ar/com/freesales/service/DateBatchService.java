package ar.com.freesales.service;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.freesales.dao.GenericDao;
import ar.com.freesales.entity.changelog.DateBatch;
import ar.com.freesales.entity.changelog.DateBatchSequence;

@Service
public class DateBatchService {
	
	@Autowired
	private GenericDao genericDao;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false)
	public Long buildDateBatch(Set<Date> dates){
		//genero el lote de fechas
		Long idLote = (Long)genericDao.save(new DateBatchSequence());
		DateBatch dateBatch  = null;
		for (Date date : dates) {
			dateBatch = new DateBatch();
			dateBatch.setIdDateBatch(idLote);
			dateBatch.setDate(date);
			genericDao.save(dateBatch);
		}
		return idLote;
	}

	
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false)
	public Long buildDateBatch(Date date){
		//genero el lote de fechas
		Long idLote = (Long)genericDao.save(new DateBatchSequence());
		DateBatch dateBatch  = null;
		dateBatch = new DateBatch();
		dateBatch.setIdDateBatch(idLote);
		dateBatch.setDate(date);
		genericDao.save(dateBatch);
		return idLote;
	}

}
