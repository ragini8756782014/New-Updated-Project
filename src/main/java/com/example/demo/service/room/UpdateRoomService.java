package com.example.demo.service.room;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.demo.entity.BookingDetails;
import com.example.demo.entity.Rooms;
import com.example.demo.repository.RoomsRepository;
import com.example.demo.response.BaseResponse;

@Service
public class UpdateRoomService {

	@Autowired
	private RoomsRepository roomsRepository;

	/**
	 * to Update a new Room.
	 *
	 * @param rooms - Rooms
	 * @return response
	 */
	public BaseResponse updateRoom(Rooms rooms) {
		BaseResponse baseResponse = new BaseResponse();
		Optional<Rooms> databaseRoom = roomsRepository.findById(rooms.getRoom_id());
		if (databaseRoom.isPresent()) {
			Rooms r = convertEntity(rooms);
			roomsRepository.save(r);
		} else {
			baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			baseResponse.setMessage("your room id is do not exist in database");
			return baseResponse;
		}
		baseResponse.setStatus(HttpStatus.ACCEPTED.value());
		baseResponse.setMessage("your room has been successfully registered");
		return baseResponse;
	}

	/**
	 * to update few things in Room.
	 *
	 * @param rooms - Rooms
	 * @return Room
	 */
	private Rooms convertEntity(Rooms rooms) {
		rooms.setAvailability(true);
		rooms.set_checked_in(false);
		rooms.set_checked_out(false);
		return rooms;
	}
	
	
	/**
	 * to update a new Room.
	 *
	 * @param id - Rooms id
	 * @param fields - Rooms details (key value pair)
	 * @return response
	 */
	public BaseResponse updateRoomsUsingPatch(Long id, Map<String, Object> fields) {
		Optional<Rooms> databaseRoom = roomsRepository.findById(id);
		BaseResponse baseresponse = new BaseResponse();
		if (databaseRoom.isPresent()) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(BookingDetails.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, databaseRoom, value);
			});
			roomsRepository.save( databaseRoom.get());
			baseresponse.setStatus(HttpStatus.ACCEPTED.value());
			baseresponse.setMessage("Your room is updated");
			return baseresponse;
		}
		baseresponse.setStatus(HttpStatus.BAD_REQUEST.value());
		baseresponse.setMessage("this id do not exist ");
		return baseresponse;
	}
	
	
	
	
	
	
	
	

}
