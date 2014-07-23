package ar.com.freesalesview.server.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.CustomConverter;

import ar.com.freesales.dao.ChangeLogDao;
import ar.com.freesales.entity.changelog.DateBatch;

public class DateBatchMapping implements CustomConverter {
	
	private ChangeLogDao changeLogDao;

	@Override
	public Object convert(Object destination, Object source,
			Class<?> destClass, Class<?> sourceClass) {
		if (source == null) {
			return null;
		}
		if (!(source instanceof Long)){
			return null;
		}
		List<Date> dates = new ArrayList<Date>();
		List<DateBatch> datesBatchs = this.changeLogDao.getDatesByBatch((Long)source);
		for (DateBatch dateBatch : datesBatchs) {
			dates.add(dateBatch.getDate());
		}
		return dates;
	}
	
	public void setChangeLogDao(ChangeLogDao changeLogDao) {
		this.changeLogDao = changeLogDao;
	}

}
