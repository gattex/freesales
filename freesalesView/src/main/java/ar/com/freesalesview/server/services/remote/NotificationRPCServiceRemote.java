package ar.com.freesalesview.server.services.remote;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.dozer.DozerBeanMapper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.freesales.dao.GenericDao;
import ar.com.freesales.dao.UnitOfWorkDao;
import ar.com.freesales.entity.changelog.ChangeLog;
import ar.com.freesales.entity.changelog.UnitOfWork;
import ar.com.freesales.enums.ActionEnum;
import ar.com.freesalesview.client.dtos.UnitOfWorkDTO;
import ar.com.freesalesview.client.dtos.UnitOfWorkItemDTO.ChangeStateDTO;
import ar.com.freesalesview.client.enums.ChangeStatusEnum;
import ar.com.freesalesview.client.services.NotificationRPCService;
import ar.com.freesalesview.server.utils.LogRemoteServiceServlet;


@SuppressWarnings("serial")
public class NotificationRPCServiceRemote extends LogRemoteServiceServlet implements NotificationRPCService{

	private DozerBeanMapper mapper;
	
	private UnitOfWorkDao unitOfWorkDao;
	
	private GenericDao genericDao;
	
	@Override
	public void init() throws ServletException {
		super.init();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		this.mapper = context.getBean(DozerBeanMapper.class);
		this.unitOfWorkDao = context.getBean(UnitOfWorkDao.class);
		this.genericDao = context.getBean(GenericDao.class);
	}
	
	@Override
	public List<UnitOfWorkDTO> getLastUnitsOfWorks(Long idHotel) {
		List<UnitOfWorkDTO> unitsOfWorksDTOs = new ArrayList<UnitOfWorkDTO>();
		List<UnitOfWork> lastUnitOfWork = this.unitOfWorkDao.getLastUnitOfWorkByHotel(idHotel);
		for (UnitOfWork uow : lastUnitOfWork) {
			UnitOfWorkDTO unitOfWorkDTO = mapper.map(uow, UnitOfWorkDTO.class);
			unitOfWorkDTO.setActionEnum(transformActionEnum(uow.getActionEnum()));
			unitsOfWorksDTOs.add(unitOfWorkDTO);
		}
		return unitsOfWorksDTOs;
	}
	
	private ar.com.freesalesview.client.enums.ActionEnum transformActionEnum(ActionEnum action){
		if (ActionEnum.CHANGE_RATE.equals(action)){
			return ar.com.freesalesview.client.enums.ActionEnum.RATE_UPDATE;
		}else if (ActionEnum.CHANGE_AVAILABILITY.equals(action)){
			return ar.com.freesalesview.client.enums.ActionEnum.AVAILABILITY;
		}else if (ActionEnum.CHANGE_STOCK.equals(action)){
			return ar.com.freesalesview.client.enums.ActionEnum.OPEN_CLOSE;
		}
		return null;
	}

	@Override
	public ChangeStateDTO checkStatus(Long idHotel, ChangeStateDTO dto) {
		ChangeLog changeLog = this.genericDao.get(ChangeLog.class,dto.getIdChangeLog());
		dto.setChangeStatusEnum(ChangeStatusEnum.valueOf(changeLog.getStatusEnum().name()));
		return dto;
	}
}
