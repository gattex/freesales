package ar.com.freesalesview.server.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;

import ar.com.freesales.dao.ChangeLogDao;
import ar.com.freesales.entity.changelog.ChangeLog;
import ar.com.freesales.entity.changelog.DateBatch;
import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO;
import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO.ChangeStateDTO;
import ar.com.freesalesview.client.enums.ChangeStatusEnum;

public class ChangeLogUnitOfWorkItemMapping implements CustomConverter {

	private DozerBeanMapper mapper;
	
	private ChangeLogDao changeLogDao;
	
	@Override
	public Object convert(Object destination, Object source,
			Class<?> destClass, Class<?> sourceClass) {
		if (source == null) {
			return null;
		}
		if (!(source instanceof Set)){
			return null;
		}
		
		List<UnitOfWorkItemDTO> units = new ArrayList<UnitOfWorkItemDTO>();
		Set<ChangeLog> changes = (Set<ChangeLog>)source;
		UnitOfWorkItemDTO unit = null;
		for (ChangeLog changeLog : changes) {
			unit = mapper.map(changeLog, UnitOfWorkItemDTO.class);
			unit.setIsGroupable(changeLog.getHotelOta().getOta().supportMassiveAction(changeLog.getUnitOfWork().getActionEnum()));
			this.chargeState(unit, changeLog);
			UnitOfWorkItemDTO field = getByFields(units,unit);
			if (field != null){
				field.getChangesStates().addAll(unit.getChangesStates());
			}else{
				units.add(unit);
			}
		}
		return units;
	}
	
	private void chargeState(UnitOfWorkItemDTO unit, ChangeLog change){
		ChangeStateDTO changeStateDTO = new ChangeStateDTO();
		changeStateDTO.setChangeStatusEnum(ChangeStatusEnum.valueOf(change.getStatusEnum().name()));
		changeStateDTO.setDates(getDatesByIdBatch(change.getIdDateBatch()));
		changeStateDTO.setIdChangeLog(change.getId());
		unit.addChangeState(changeStateDTO);
	}
	
	private List<Date> getDatesByIdBatch(Long idDateBatch){
		List<Date> dates = new ArrayList<Date>();
		List<DateBatch> datesBatchs = this.changeLogDao.getOrderDatesByBatch(idDateBatch);
		for (DateBatch dateBatch : datesBatchs) {
			dates.add(dateBatch.getDate());
		}
		return dates;
	}
	
	
	private UnitOfWorkItemDTO getByFields(List<UnitOfWorkItemDTO> units, UnitOfWorkItemDTO unit){
		for (UnitOfWorkItemDTO unitOfWorkItemDTO : units) {
			if (unit.equals(unitOfWorkItemDTO)){
				return unitOfWorkItemDTO;
			}
		}
		return null;
	}
	
	public void setMapper(DozerBeanMapper mapper) {
		this.mapper = mapper;
	}
	
	public void setChangeLogDao(ChangeLogDao changeLogDao) {
		this.changeLogDao = changeLogDao;
	}
}
