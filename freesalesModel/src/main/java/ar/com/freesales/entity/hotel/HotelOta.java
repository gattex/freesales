package ar.com.freesales.entity.hotel;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.com.freesales.dto.RoomCategoryDTO;
import ar.com.freesales.dto.RoomSubcategoryDTO;
import ar.com.freesales.entity.ota.Ota;
import ar.com.freesales.entity.room.HotelOtaRoomType;
import ar.com.freesales.entity.room.RoomType;
import ar.com.freesales.enums.ActionEnum;
import ar.com.freesales.enums.RoomCategoryEnum;

@SuppressWarnings("serial")
@Entity
@Table(name = "HOTEL_OTA")
public class HotelOta implements Serializable{
	
	private Long id;
	private Hotel hotel;
	private Ota ota;
	private String user;
	private String pass;
	private Set<HotelOtaRoomType> hotelOtaRoomTypes;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	@ManyToOne
    @JoinColumn(name = "ID_HOTEL")
	public Hotel getHotel() {
		return hotel;
	}
	
	@ManyToOne
    @JoinColumn(name = "ID_OTA")
	public Ota getOta() {
		return ota;
	}
	
	@OneToMany(mappedBy = "hotelOta")
	public Set<HotelOtaRoomType> getHotelOtaRoomTypes() {
		return hotelOtaRoomTypes;
	}
	
	@Column (name = "PASS")
	public String getPass() {
		return pass;
	}
	
	@Column (name = "USER")
	public String getUser() {
		return user;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	public void setOta(Ota ota) {
		this.ota = ota;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public void setHotelOtaRoomTypes(Set<HotelOtaRoomType> hotelOtaRoomTypes) {
		this.hotelOtaRoomTypes = hotelOtaRoomTypes;
	}

//	business method
	@Transient
	public String getRoomTypeCode(RoomType roomType){
		String roomTypeCodeAux = null;
		for (HotelOtaRoomType hotelOtaRoomType : hotelOtaRoomTypes) {
			if(hotelOtaRoomType.getRoomType().equals(roomType)){
				roomTypeCodeAux = hotelOtaRoomType.getRoomTypeCode();
			}
		}
		return roomTypeCodeAux;
	}

	@Transient
	public String getRoomTypeCategoryCode(RoomType roomType){
		String roomTypeCodeAux = null;
		for (HotelOtaRoomType hotelOtaRoomType : hotelOtaRoomTypes) {
			RoomType roomType2 = hotelOtaRoomType.getRoomType();
			if(roomType2.getRoomSubcategoryEnum() == null && roomType2.getRoomCategoryEnum().equals(roomType.getRoomCategoryEnum())){
				roomTypeCodeAux = hotelOtaRoomType.getRoomTypeCode();
			}
		}
		return roomTypeCodeAux;
	}
	
	@Transient
	public String getRoomTypeCodeOpenClose(RoomType roomType){
		String roomTypeCodeAux = null;
		for (HotelOtaRoomType hotelOtaRoomType : hotelOtaRoomTypes) {
			if(hotelOtaRoomType.getRoomType().equals(roomType)){
				if(hotelOtaRoomType.hasAdditionalInfo()){
					roomTypeCodeAux = ota.getOpenCloseAdditionalInfo(hotelOtaRoomType);
				}else{
					roomTypeCodeAux = hotelOtaRoomType.getRoomTypeCode();
				}
			}
		}
		return roomTypeCodeAux;
	}
	
	public boolean supportMassiveAction(ActionEnum actionEnum) {
		// TODO Auto-generated method stub
		return ota.supportMassiveAction(actionEnum);
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	//este metodo me sirve para saber que categorias y subcategorias
//	muestro en cada pantalla
	public Set<RoomCategoryDTO> getCategoriesDTO(ActionEnum actionEnum){
		Set<RoomCategoryDTO> categoryDTOs = new HashSet<RoomCategoryDTO>();
		Collection<HotelOtaRoomType> soloCategorias = CollectionUtils.select(hotelOtaRoomTypes, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				return ((HotelOtaRoomType)arg0).getRoomType().getRoomSubcategoryEnum() == null;
			}
		});
		
		//creo el dto de las categorias
		for (HotelOtaRoomType soloCategoria : soloCategorias) {
			if(soloCategoria.applyAction(actionEnum)){
				RoomType roomType = soloCategoria.getRoomType();
				RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO();
				roomCategoryDTO.setRoomCategoryEnum(roomType.getRoomCategoryEnum());
				roomCategoryDTO.setIdRoomType(roomType.getId());
				categoryDTOs.add(roomCategoryDTO);
			}
		}
		
		//le agrego las subcategorias
		for (HotelOtaRoomType hotelOtaRoomType : hotelOtaRoomTypes) {
			RoomType roomType = hotelOtaRoomType.getRoomType();
			if(hotelOtaRoomType.applyAction(actionEnum) && roomType.getRoomSubcategoryEnum()!=null){
				final RoomCategoryEnum categoryEnum = roomType.getRoomCategoryEnum();
//				tengo que buscar en la listas de dtos de categorias 
//				esta categoria para agregarle la subcategoria
				RoomCategoryDTO categoryDTO = (RoomCategoryDTO)CollectionUtils.find(categoryDTOs, new Predicate() {
					
					@Override
					public boolean evaluate(Object arg0) {
						return categoryEnum.equals(((RoomCategoryDTO)arg0).getRoomCategoryEnum());
					}
				});
				RoomSubcategoryDTO roomSubcategoryDTO = new RoomSubcategoryDTO();
				roomSubcategoryDTO.setIdRoomType(roomType.getId());
				roomSubcategoryDTO.setRoomSubcategoryEnum(roomType.getRoomSubcategoryEnum());
				
				categoryDTO.addRoomSubcategoryDTO(roomSubcategoryDTO);
			}
			
		}
		
		
		
		return categoryDTOs;
	}
	
}
