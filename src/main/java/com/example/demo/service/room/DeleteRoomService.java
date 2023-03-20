package com.example.demo.service.room;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Rooms;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.RoomsRepository;
import com.example.demo.response.BaseResponse;

@Service
public class DeleteRoomService {

	@Autowired
	private RoomsRepository roomsRepository;

	/**
	 * to Delete Room.
	 *
	 * @param id - Rooms id
	 * @return response
	 */
	public BaseResponse deleteRoom(Long id) {
		validate(id);
		BaseResponse baseresponse = new BaseResponse();
		Optional<Rooms> dbRoom = roomsRepository.findById(id);
		if (dbRoom.isPresent()) {
			roomsRepository.deleteById(id);
			baseresponse.setStatus(HttpStatus.ACCEPTED.value());
			baseresponse.setMessage("your room has been successfully deleted");
			return baseresponse;
		}
		baseresponse.setStatus(HttpStatus.BAD_REQUEST.value());
		baseresponse.setMessage("your room id do not exist");
		return baseresponse;
	}

	private void validate(Long id) {
		if (id <= 0 || id != (Long) id) {
			throw new ValidationException("FV001", "roomId cannot be null or empty");
		}
	}

}
