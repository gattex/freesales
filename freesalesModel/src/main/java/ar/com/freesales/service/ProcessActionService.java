package ar.com.freesales.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.freesales.dao.GenericDao;
import ar.com.freesales.entity.changelog.UnitOfWork;
import ar.com.freesales.entity.hotel.HotelOta;
import ar.com.freesales.entity.room.RoomType;
import ar.com.freesales.entity.user.UserDetail;
import ar.com.freesales.enums.ActionEnum;

@Service
public class ProcessActionService {
	
	private Logger log = Logger.getLogger(ProcessActionService.class);
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private DateBatchService dateBatchService;
	
	@Autowired
	private ProcessBatchService processBatchService;
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void doAction(Set<Date> dates, Set<Long> idsRoomType, Set<Long> idsHotelOtas, UserDetail userDetail, ActionEnum actionEnum , Integer value){
		//primero guardo la unidad de trabajo
		UnitOfWork unitOfWork = new UnitOfWork();
		unitOfWork.setActionEnum(actionEnum);
		unitOfWork.setDate(new Date());
		unitOfWork.setUserDetail(userDetail);
		unitOfWork.setValue(value);
		genericDao.save(unitOfWork);
		
		//genero el lote de fechas
		Long idLoteGenerico = dateBatchService.buildDateBatch(dates);
		
		//luego guardo los CL
		
		//recuero los roomtypes y los hotelOta
		Set<HotelOta> hotelOtas = new HashSet<HotelOta>();
		for (Long idHotelOta : idsHotelOtas) {
			hotelOtas.add(genericDao.get(HotelOta.class, idHotelOta));
		}
		for (Long idRoomType : idsRoomType) {
			//TODO: me parece q estoy yendo al pedo a la base
			// podría directamente crear el objeto con new y ponerle el id
			// total es para persistir y debertía tener un cascade none
			RoomType roomType = genericDao.get(RoomType.class, idRoomType);
			log.info("persisto el CL para el roomType= " +idRoomType);
			//persisto los changelog
			processBatchService.saveBatch(dates, roomType, hotelOtas, actionEnum, unitOfWork, idLoteGenerico);
		}
		
		
		//TODO: esto va a la cola, pero ahora solo es un id
		Long idUnitOfWork = unitOfWork.getId();
		log.info("proceso la unidad de trabajo: " + idUnitOfWork);
//		processBatchService.processUnitOfWork(idUnitOfWork);
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void doAction(Set<Date> dates, Set<Long> idsRoomType, Set<Long> idsHotelOtas, UserDetail userDetail, ActionEnum actionEnum , boolean isOpen){
		doAction(dates, idsRoomType, idsHotelOtas, userDetail, actionEnum , isOpen?1:0);
	}
	
}
