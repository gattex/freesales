package ar.com.freesales.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.freesales.dao.ChangeLogDao;
import ar.com.freesales.dao.GenericDao;
import ar.com.freesales.entity.changelog.ChangeLog;
import ar.com.freesales.entity.changelog.DateBatch;
import ar.com.freesales.entity.changelog.UnitOfWork;
import ar.com.freesales.entity.hotel.HotelOta;
import ar.com.freesales.entity.ota.Ota;
import ar.com.freesales.entity.room.RoomType;
import ar.com.freesales.enums.ActionEnum;
import ar.com.freesales.enums.StatusEnum;
import ar.com.freesales.exception.OtaDoActionException;
import ar.com.freesales.exception.OtaLoginException;

@Service
public class ProcessBatchService {
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private ChangeLogDao changeLogDao;
	
	@Autowired
	private DateBatchService dateBatchService;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void saveBatch(Set<Date> dates, RoomType roomType, Set<HotelOta> hotelOtas, ActionEnum actionEnum, UnitOfWork unitOfWork, Long idLoteGenerico){
		
		//armo el changeLog por cada ota
		ChangeLog changeLog = null; 
		for (HotelOta hotelOta : hotelOtas) {
			
			//si no se banca el masivo, le tengo que generar n lotes donde cada uno tiene solo una fecha
			if(hotelOta.supportMassiveAction(actionEnum)){
				changeLog = buildChangeLog(roomType,idLoteGenerico, hotelOta, unitOfWork);
				genericDao.save(changeLog);
			}else{
//				le tengo que generar un lote nuevo por cada fecha 
				for (Date date : dates) {
					Long idLote = dateBatchService.buildDateBatch(date);
					changeLog = buildChangeLog(roomType, idLote, hotelOta, unitOfWork);
					genericDao.save(changeLog);
				}
			}
		}
	}

	private ChangeLog buildChangeLog(RoomType roomType,
			Long idLoteGenerico, HotelOta hotelOta, UnitOfWork unitOfWork) {
		ChangeLog changeLog = new ChangeLog();
		changeLog.setIdDateBatch(idLoteGenerico);
		changeLog.setHotelOta(hotelOta);
		changeLog.setRoomType(roomType);
		changeLog.setStatusEnum(StatusEnum.INIT);
		changeLog.setUnitOfWork(unitOfWork);
		return changeLog;
	}
	
	//FIXME: deberia ser atomico por change log...esta mal así
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void processUnitOfWork(Long idUnitOfWork){
		boolean hasError = false;
		String error = null;
		
		UnitOfWork unitOfWork = genericDao.get(UnitOfWork.class, idUnitOfWork);
		
		
		//obtengo las fechas
		
		//obtengo la lista de changelog q no se procesaron de ese lote
		Set<ChangeLog> changeLogs = unitOfWork.getChangeLogs();
		
		//le digo a cada Ota q lo procese
		Ota ota = null;
		for (ChangeLog changeLog : changeLogs) {
			
			Long idLote = changeLog.getIdDateBatch();
			List<DateBatch> dateBatchs = changeLogDao.getDatesByBatch(idLote);
			
			HotelOta hotelOta = changeLog.getHotelOta();
			ota = hotelOta.getOta();
			try {
				RoomType roomTypeEnum = changeLog.getRoomType();

				switch (changeLog.getUnitOfWork().getActionEnum()) {
				case CHANGE_AVAILABILITY:
					ota.doChangeAvailability(dateBatchs, hotelOta.getRoomTypeCodeOpenClose(roomTypeEnum), changeLog.isOpen(), hotelOta.getUser(), hotelOta.getPass());
					break;
				case CHANGE_RATE:
					ota.doChangeRate(dateBatchs,  hotelOta.getRoomTypeCategoryCode(roomTypeEnum), hotelOta.getRoomTypeCode(roomTypeEnum), changeLog.getValue(), hotelOta.getUser(), hotelOta.getPass());
					break;
				case CHANGE_STOCK:
					ota.doChangeStock(dateBatchs, hotelOta.getRoomTypeCategoryCode(roomTypeEnum), changeLog.getValue(), hotelOta.getUser(), hotelOta.getPass());
					break;
				default:
					break;
				}
				
			} catch (OtaLoginException e) {
				hasError = true;
				//TODO FIXME i18n
				error = "Error al loguearse";
			} catch (OtaDoActionException e) {
				hasError = true;
				//TODO FIXME i18n + que el error me lo pase la exception
				error = "Error general";
			}
			//actualizo el estado
			if(hasError){
				changeLog.setStatusEnum(StatusEnum.FAIL);
				changeLog.setErrorDetail(error);
			}else{
				changeLog.setStatusEnum(StatusEnum.SUCCESS);
			}
			genericDao.update(changeLog);
		}
		unitOfWork.setFinish(Boolean.TRUE);
		genericDao.update(unitOfWork);
		
	}
	
}
