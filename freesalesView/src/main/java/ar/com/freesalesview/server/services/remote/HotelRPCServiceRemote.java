package ar.com.freesalesview.server.services.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import org.dozer.DozerBeanMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ar.com.freesales.dao.GenericDao;
import ar.com.freesales.dto.RoomCategoryDTO;
import ar.com.freesales.entity.hotel.Hotel;
import ar.com.freesales.entity.hotel.HotelOta;
import ar.com.freesales.entity.user.UserDetail;
import ar.com.freesales.enums.ActionEnum;
import ar.com.freesales.service.ProcessActionService;
import ar.com.freesales.service.RoomService;
import ar.com.freesalesview.client.dtos.GroupRoomDTO;
import ar.com.freesalesview.client.dtos.HotelDTO;
import ar.com.freesalesview.client.dtos.OtaDTO;
import ar.com.freesalesview.client.dtos.ResultChangeDTO;
import ar.com.freesalesview.client.dtos.RoomDTO;
import ar.com.freesalesview.client.services.HotelRPCService;
import ar.com.freesalesview.server.entities.UserLogged;
import ar.com.freesalesview.server.utils.DateUtils;
import ar.com.freesalesview.server.utils.LogRemoteServiceServlet;


@SuppressWarnings("serial")
public class HotelRPCServiceRemote extends LogRemoteServiceServlet implements HotelRPCService{

	private GenericDao genericDao;
	
	private RoomService roomService;
	
	private DozerBeanMapper mapper;
	
	private ProcessActionService processActionService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		this.genericDao=context.getBean(GenericDao.class);
		this.roomService = context.getBean(RoomService.class);
		this.mapper = context.getBean(DozerBeanMapper.class);
		this.processActionService = context.getBean(ProcessActionService.class);
	}
	
	@Override
	public HotelDTO getHotel() {
		HotelDTO hotelDTO = new HotelDTO();
		UserDetail userDetail = getUserDetail();
		if (userDetail == null){
			return null; //TODO NO ME GUSTA 
		}
		Hotel hotel = userDetail.getHotel();
		hotelDTO.setId(hotel.getId());
		hotelDTO.setName(hotel.getName());
		createHotelRoom(hotelDTO, hotel);
		createHotelOta(hotelDTO, hotel);
		return hotelDTO;
	}

	private UserDetail getUserDetail() {
		UserLogged userLogged = (UserLogged)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetail userDetail = this.genericDao.get(UserDetail.class,userLogged.getUser().getId());
		return userDetail;
	}

	private void createHotelOta(HotelDTO hotelDTO, Hotel hotel) {
		Set<HotelOta> hotelOtas = hotel.getHotelOtas();
		hotelDTO.setOtasByAction(createOtaDto(hotelOtas),ar.com.freesalesview.client.enums.ActionEnum.RATE_UPDATE);
		hotelDTO.setOtasByAction(createOtaDto(hotelOtas),ar.com.freesalesview.client.enums.ActionEnum.AVAILABILITY);
		hotelDTO.setOtasByAction(createOtaDto(hotelOtas),ar.com.freesalesview.client.enums.ActionEnum.OPEN_CLOSE);
	}

	private List<OtaDTO> createOtaDto(Set<HotelOta> hotelOtas) {
		List<OtaDTO> otas = new ArrayList<OtaDTO>(); 
		for (HotelOta hotelOta : hotelOtas) {
			OtaDTO ota = mapper.map(hotelOta.getOta(), OtaDTO.class);
			ota.setHotelOtaId(hotelOta.getId());
			otas.add(ota);
		}
		return otas;
	}
	
	private void createHotelRoom(HotelDTO hotelDTO, Hotel hotel) {
		Set<RoomCategoryDTO> roomsRate = this.roomService.getRoomsCategoryDTO(hotel.getId(), ActionEnum.CHANGE_RATE);
		Set<RoomCategoryDTO> roomsAvailability = this.roomService.getRoomsCategoryDTO(hotel.getId(), ActionEnum.CHANGE_AVAILABILITY);
		Set<RoomCategoryDTO> roomsStock = this.roomService.getRoomsCategoryDTO(hotel.getId(), ActionEnum.CHANGE_STOCK);
		hotelDTO.setRoomsByAction(this.createRoomDto(roomsRate), ar.com.freesalesview.client.enums.ActionEnum.RATE_UPDATE);
		hotelDTO.setRoomsByAction(this.createRoomDto(roomsAvailability), ar.com.freesalesview.client.enums.ActionEnum.AVAILABILITY);
		hotelDTO.setRoomsByAction(this.createRoomDto(roomsStock), ar.com.freesalesview.client.enums.ActionEnum.OPEN_CLOSE);
	}

	private List<GroupRoomDTO> createRoomDto(Set<RoomCategoryDTO> roomsRate) {
		List<GroupRoomDTO> rooms = new ArrayList<GroupRoomDTO>(); 
		for (RoomCategoryDTO roomCategoryDTO : roomsRate) {
			GroupRoomDTO map = mapper.map(roomCategoryDTO, GroupRoomDTO.class);
			rooms.add(map);
		}
		return rooms;
	}

	@Override
	public ResultChangeDTO doChangeRate(Long idHotel, List<RoomDTO> rooms, 	List<OtaDTO> otas, List<GroupRoomDTO> groupRooms, 
			List<String> dates, Double value) {
		String[] arrayDates = dates.toArray(new String[dates.size()]);
		Set<Date> selectedDates = new HashSet<>(DateUtils.toDate(arrayDates));
		Set<Long> idRooms = this.getIdRooms(rooms);
		Set<Long> idHotelOta = this.getIdHotelOta(otas);
		this.processActionService.doAction(selectedDates,idRooms,idHotelOta,this.getUserDetail(),ActionEnum.CHANGE_RATE,value.intValue());
		return new ResultChangeDTO();
	}

	@Override
	public ResultChangeDTO doChangeAvailability(Long idHotel, List<RoomDTO> rooms, List<OtaDTO> otas,List<GroupRoomDTO> groupRooms, 
			List<String> dates,	Long numberAvailability) {
		String[] arrayDates = dates.toArray(new String[dates.size()]);
		Set<Date> selectedDates = new HashSet<>(DateUtils.toDate(arrayDates));
		Set<Long> idRooms = this.getIdRooms(rooms);
		Set<Long> idHotelOta = this.getIdHotelOta(otas);
		this.processActionService.doAction(selectedDates,idRooms,idHotelOta,this.getUserDetail(),ActionEnum.CHANGE_AVAILABILITY,numberAvailability.intValue());
		return new ResultChangeDTO();
	}
	
	@Override
	public ResultChangeDTO doChangeOpenClose(Long idHotel, List<RoomDTO> rooms,
			List<OtaDTO> otas, List<GroupRoomDTO> groupRooms,
			List<String> dates, boolean isOpen) {
		String[] arrayDates = dates.toArray(new String[dates.size()]);
		Set<Date> selectedDates = new HashSet<>(DateUtils.toDate(arrayDates));
		Set<Long> idRooms = this.getIdGroupRooms(groupRooms);
		Set<Long> idHotelOta = this.getIdHotelOta(otas);
		this.processActionService.doAction(selectedDates,idRooms,idHotelOta,this.getUserDetail(),ActionEnum.CHANGE_STOCK,isOpen);
		return null;
	}

	private Set<Long> getIdRooms(List<RoomDTO> rooms){
		Set<Long> idRooms = new HashSet<Long>();
		for (RoomDTO room : rooms) {
			idRooms.add(room.getIdSubcategory());
		}
		return idRooms;
	}
	
	private Set<Long> getIdGroupRooms(List<GroupRoomDTO> groupRoomDTOs){
		Set<Long> idRooms = new HashSet<Long>();
		for (GroupRoomDTO group : groupRoomDTOs) {
			idRooms.add(group.getIdRoomType());
		}
		return idRooms;
	}
	
	private Set<Long> getIdHotelOta(List<OtaDTO> otas){
		Set<Long> idHotelOta = new HashSet<Long>();
		for (OtaDTO ota : otas) {
			idHotelOta.add(ota.getHotelOtaId());
		}
		return idHotelOta;
	}
}
