package ar.com.freesales.service;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ar.com.freesales.dto.RoomCategoryDTO;
import ar.com.freesales.dto.RoomSubcategoryDTO;
import ar.com.freesales.enums.ActionEnum;

@ContextConfiguration(locations={"/ar/com/freesales/freesalesApplicationContextTest.xml"})
@TransactionConfiguration(transactionManager="freesalesTransactionManager")
public class RoomServiceTest extends AbstractTransactionalJUnit4SpringContextTests{ 

	@Autowired
	private RoomService roomService;
	
	@Test
	public void testGetRoomsCategoryDTO() {
		
		ActionEnum[] actionEnums = ActionEnum.values();
		
		for (int i = 0; i < actionEnums.length; i++) {
			System.out.println("para " + actionEnums[i]  );
			
			Set<RoomCategoryDTO> roomCategoryDTOs = roomService.getRoomsCategoryDTO(1l, actionEnums[i]);
			
			for (RoomCategoryDTO roomCategoryDTO : roomCategoryDTOs) {
				System.out.print("\t" + roomCategoryDTO.getIdRoomType());
				System.out.println("\t" + roomCategoryDTO.getRoomCategoryEnum());
				for (RoomSubcategoryDTO roomSubCategoryDTO : roomCategoryDTO.getRoomSubcategoryDTOs()) {
					System.out.print("\t\t" + roomSubCategoryDTO.getIdRoomType());
					System.out.println("\t\t" + roomSubCategoryDTO.getRoomSubcategoryEnum());
				}
			}
			System.out.println("\n");
		}
		
	}

}
