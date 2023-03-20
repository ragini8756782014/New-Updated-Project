package com.example.demo.service.room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.classes.RoomReturn;
import com.example.demo.entity.Rooms;

@Service
public class FindRoomNoService {

	@Autowired
	private GetAllRoomService getAllRoomService;

	/**
	 * to get available rooms.
	 *
	 * @param category - Rooms Category
	 * @param noOfRooms - number of rooms want to book
	 * @return available booked rooms
	 */
	public List<RoomReturn> findRoomNo(String category, int noOfRooms) {
		List<Rooms> availableRooms = getAllRoomService.getRoom().stream().filter(e ->(e.isAvailability()==true))
				.collect(Collectors.toList());
		List<RoomReturn> roomReturnList = new ArrayList<>();
		List<Rooms> allottedRooms = createEntity(availableRooms, category, noOfRooms);
		allottedRooms.forEach(item -> {
			RoomReturn roomReturn = new RoomReturn();
			roomReturn.setRoomId(item.getRoom_id());
			roomReturn.setRoomNo(item.getRoom_number());
			roomReturn.setBillAmount(item.getPrice_per_day());
			roomReturn.setPricePerHour(item.getPrice_per_hours());
			roomReturn.setOccupancy(item.getOccupancy());
			roomReturnList.add(roomReturn);
		});

		return roomReturnList;

	}

	/**
	 * to available room.
	 *
	 * @param category - Rooms Category
	 * @param noOfRooms - number of rooms want to book
	 * @param availableRooms - Rooms that are available
	 * @return available booked rooms
	 */
	private List<Rooms> createEntity(List<Rooms> availableRooms, String category, int no_of_rooms) {

		List<Rooms> rooms = new ArrayList<>();
		for (Rooms i : availableRooms) {
			if (no_of_rooms != 0) {
				if (category.equals(i.getCategory_type())) {
					i.setAvailability(false);
					i.set_checked_in(true);
					rooms.add(i);
					no_of_rooms--;
				}
			}
		}
		return rooms;
	}

}
