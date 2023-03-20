package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Rooms;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.room.CreateRoomService;
import com.example.demo.service.room.DeleteRoomService;
import com.example.demo.service.room.GetAllRoomByIdService;
import com.example.demo.service.room.GetAllRoomService;
import com.example.demo.service.room.UpdateRoomService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RoomsController {

	@Autowired
	private CreateRoomService createRoomService;

	@Autowired
	private DeleteRoomService deleteRoomService;

	@Autowired
	private GetAllRoomByIdService getAllRoomByIdService;

	@Autowired
	private GetAllRoomService getAllRoomService;

	@Autowired
	private UpdateRoomService updateRoomService;

	/**
     * To Add a new Room.
     *
     * @param rooms - Room's details
     * @return ResponseEntity
     */
	@PostMapping("/rooms")
	public ResponseEntity<BaseResponse> createRoom(@RequestBody Rooms rooms) {
		log.info("creating new rooms ");
		return new ResponseEntity<>(createRoomService.createRoom(rooms), HttpStatus.CREATED);
	}
     
	/**
     * To get List of all Rooms.
     *
     * @return ResponseEntity
     */
	@GetMapping("/rooms")
	public ResponseEntity<List<Rooms>> getRoom() {
		log.info("getting all rooms");
		return new ResponseEntity<>(getAllRoomService.getRoom(), HttpStatus.OK);
	}

	/**
     * To get Details of Room with Room's ID.
     *
     * @param id - Room's ID
     * @return ResponseEntity
     */
	@GetMapping("/rooms/{id}")
	public ResponseEntity<Optional<Rooms>> getRoomById(@PathVariable("id") Long id) {
		log.info("getting rooms using room id ");
		return new ResponseEntity<>(getAllRoomByIdService.getRoomById(id), HttpStatus.FOUND);
	}
 
	/**
     * to Delete a Customer.
     *
     * @param id - Customer's ID
     * @return ResponseEntity
     */
	@DeleteMapping("/rooms/{id}")
	public ResponseEntity<BaseResponse> deleteRoom(@PathVariable("id") Long id) {
		log.info("deleting room using room id");
		return new ResponseEntity<>(deleteRoomService.deleteRoom(id), HttpStatus.OK);
	}

	/**
     * to Update Room Details.
     *
     * @param rooms   - Customer's details
     * @return ResponseEntity
     */
	@PutMapping("/rooms")
	public ResponseEntity<BaseResponse> updateRooms(@RequestBody Rooms rooms) {
		log.info("updating all details of room ");
		return new ResponseEntity<>(updateRoomService.updateRoom(rooms), HttpStatus.OK);
	}

	/**
     * to Update Room Details.
     *
     * @param id - Room's ID
     * @param fields   - specific room details which we want to update
     * @return ResponseEntity
     */
	@PatchMapping("/rooms")
	public ResponseEntity<BaseResponse> updateRoomsUsingPatch(@PathVariable("id") Long id,
			@RequestBody Map<String, Object> fields) {
		log.info("updating few details of room using patch mapping ");
		return new ResponseEntity<>(updateRoomService.updateRoomsUsingPatch(id, fields), HttpStatus.OK);
	}

}
