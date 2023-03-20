package com.example.demo.service.room;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Rooms;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.RoomsRepository;

@Service
public class GetAllRoomByIdService {

	@Autowired
	private RoomsRepository roomsRepository;

	/**
	 * to get rooms using id.
	 *
	 * @param id - Rooms id
	 * @return  rooms
	 */
	public Optional<Rooms> getRoomById(Long id) {
		validate(id);
		Optional<Rooms> dbRoom = roomsRepository.findById(id);
		if(dbRoom.isPresent())
		{return roomsRepository.findById(id);}
		else {
			throw new ValidationException("FV001", "id do not exist ");
		}

	}

	private void validate(Long id) {
		if (id <= 0 || id != (Long) id) {
			throw new ValidationException("FV001", "roomId cannot be null or empty");
		}
	}
}
