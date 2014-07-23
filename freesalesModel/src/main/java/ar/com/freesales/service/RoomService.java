package ar.com.freesales.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.freesales.dao.GenericDao;
import ar.com.freesales.dto.RoomCategoryDTO;
import ar.com.freesales.entity.hotel.Hotel;
import ar.com.freesales.entity.hotel.HotelOta;
import ar.com.freesales.enums.ActionEnum;
import ar.com.freesales.enums.RoomCategoryEnum;

@Service
public class RoomService {
	
	@Autowired
	private GenericDao genericDao;
	
	@Transactional(readOnly=true)
	public Set<RoomCategoryDTO> getRoomsCategoryDTO(Long idHotel, ActionEnum actionEnum){
		Set<RoomCategoryDTO> categoryDTOs = new HashSet<RoomCategoryDTO>();
		Hotel hotel = genericDao.get(Hotel.class, idHotel);
		
		Set<HotelOta> hotelOtas = hotel.getHotelOtas();
		
		for (HotelOta hotelOta : hotelOtas) {
			Set<RoomCategoryDTO> categoryDTOs2 = hotelOta.getCategoriesDTO(actionEnum);
			mergeSubcategories(categoryDTOs, categoryDTOs2);
		}
		
		return categoryDTOs;
	}

	//recorro la lista chica y la mergeo con la grande
	private void mergeSubcategories(Set<RoomCategoryDTO> categoryDTOs,
			Set<RoomCategoryDTO> categoryDTOs2) {
		for (RoomCategoryDTO roomCategoryDTO : categoryDTOs2) {
			final RoomCategoryEnum roomCategoryEnum = roomCategoryDTO.getRoomCategoryEnum(); 
//			por cada elemento lo busco en la grande y le agrego las subcat
			RoomCategoryDTO roomCategoryDTOAux = (RoomCategoryDTO)CollectionUtils.find(categoryDTOs, new Predicate() {
				
				@Override
				public boolean evaluate(Object arg0) {
					return roomCategoryEnum.equals(((RoomCategoryDTO)arg0).getRoomCategoryEnum());
				}
			});
//			si no estaba, lo agrego
			if(roomCategoryDTOAux==null){
				categoryDTOs.add(roomCategoryDTO);
			}else{
//				si estaba, le agrego las subcategorias
				roomCategoryDTOAux.addAllRoomSubcategoryDTO(roomCategoryDTO.getRoomSubcategoryDTOs());
			}
		}
		
	}

}
