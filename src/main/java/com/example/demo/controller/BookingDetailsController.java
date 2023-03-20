package com.example.demo.controller;

import java.text.ParseException;
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

import com.example.demo.classes.BookingDetailsReturn;
import com.example.demo.entity.BookingDetails;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.bookingdetail.CreateBookingDetailService;
import com.example.demo.service.bookingdetail.DeleteBookingDetailsService;
import com.example.demo.service.bookingdetail.GetAllBookingDetailsService;
import com.example.demo.service.bookingdetail.GetBookingDetailByIdService;
import com.example.demo.service.bookingdetail.GetBookingDetailsUsingDateService;
import com.example.demo.service.bookingdetail.UpdateBookingDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BookingDetailsController {

	@Autowired
	private CreateBookingDetailService createBookingDetailService;

	@Autowired
	private GetAllBookingDetailsService getAllBookingDetailsService;

	@Autowired
	private GetBookingDetailByIdService getBookingDetailByIdService;

	@Autowired
	private DeleteBookingDetailsService deleteBookingDetailsService;

	@Autowired
	private UpdateBookingDetailService updateBookingDetailService;

	@Autowired
	private GetBookingDetailsUsingDateService getBookingDetailsBeweenDatesService;


	/**
     * To Add a new BookingDetail.
     *
     * @param bookingDetails - Booking details
     * @return ResponseEntity
     */
	@PostMapping("/bookingDetails")
	public ResponseEntity<BookingDetailsReturn> createBookingDetails(@RequestBody BookingDetails bookingDetails) {
		log.info("Response Status to saveBookingDetails", HttpStatus.OK);
		return new ResponseEntity<>(createBookingDetailService.createBookingDetail(bookingDetails), HttpStatus.CREATED);
	}

	
	/**
     * To get List of all Booking details.
     *
     * @return ResponseEntity
     */
	@GetMapping("/bookingDetails")
	public ResponseEntity<List<BookingDetails>> getAllBookingDetails() {
		log.info("getting list of all bookingDetails");
		return new ResponseEntity<>(getAllBookingDetailsService.getAllBookingDetails(), HttpStatus.OK);
	}
    
	
	/**
     * To get Details of Booking with BookingDetail ID.
     *
     * @param id - Booking ID
     * @return ResponseEntity
     */
	@GetMapping("/bookingDetails/{id}")
	public ResponseEntity<Optional<BookingDetails>> getBookingDetailsById(@PathVariable("id") Long id) {
		log.info("getting booking detail using id");
		return new ResponseEntity<>(getBookingDetailByIdService.getBookingDetailsById(id), HttpStatus.OK);
	}

	
	/**
     * to Delete a booking.
     *
     * @param id -  Booking Id
     * @return ResponseEntity
     */
	@DeleteMapping("/bookingDetails/{id}")
	public ResponseEntity<BaseResponse> deleteBookingDetails(@PathVariable("id") Long id) {
		log.info("deleteing bookingdetails using booking id");
		return new ResponseEntity<>(deleteBookingDetailsService.deleteBookingDetails(id), HttpStatus.FOUND);
	}

	/**
     * to Update Booking Details.
     *
     * @param bookingDetails   - Booking details
     * @return ResponseEntity
     */
	@PutMapping("/bookingDetails")
	public ResponseEntity<BookingDetailsReturn> updateBookingDetails(@RequestBody BookingDetails bookingDetails) {
		log.info("updaing all details of bookingdetail using put mapping ");
		return new ResponseEntity<>(updateBookingDetailService.updateBookingDetails(bookingDetails), HttpStatus.OK);
	}

	/**
     * to Update Booking Details.
     *
     * @param id   - Booking Id
     * @param fields -key and values (json format while giving input) of bookingDetails
     * @return ResponseEntity
     */
	@PatchMapping("/bookingDetails")
	public ResponseEntity<BaseResponse> updateBookingDetailsUsingPatch(@PathVariable("id") Long id,
			@RequestBody Map<String, Object> fields) {
		log.info("updating few details of booking Details using patch mapping ");
		return new ResponseEntity<>(updateBookingDetailService.updateBookingDetailsUsingPatch(id, fields),
				HttpStatus.OK);
	}
     
	
	/**
     * to get all Booking Details Between startDate and endDate.
     *
     * @param startDate -  Booking startDate
     * @param endDate  - Booking endDate
     * @return ResponseEntity
     */
	@GetMapping("/bookingDetailsBetween/{startDate}/{endDate}")
	public ResponseEntity<List<BookingDetails>> getBookingDetailsBetweenDates(
			@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
		log.info("getting bookingDetails between two dates ");
		return new ResponseEntity<>(getBookingDetailsBeweenDatesService.getBookingDetailsBetween(startDate, endDate),
				HttpStatus.OK);
	}

	/**
     * to get all Booking Details Before startDate .
     *
     * @param startDate -  Booking startDate
     * @return ResponseEntity
     */
	@GetMapping("/bookingDetailsBefore/{startDate}")
	public ResponseEntity<List<BookingDetails>> getBookingDetailsBeforeDate(
			@PathVariable("startDate") String startDate) {
		log.info("getting bookingDetails of before a given date ");
		return new ResponseEntity<>(getBookingDetailsBeweenDatesService.getBookingDetailsBeforeDate(startDate),
				HttpStatus.OK);
	}
    
	/**
     * to get all Booking Details of that year of  startDate.
     *
     * @param startDate -  Booking startDate
     * @return ResponseEntity
     */
	@GetMapping("/bookingDetailsYear/{startDate}")
	public ResponseEntity<List<BookingDetails>> getBookingDetailsUsingYear(@PathVariable("startDate") String startDate)
			throws ParseException {
		log.info("getting booking details using year");
		return new ResponseEntity<>(getBookingDetailsBeweenDatesService.getBookingDetailsUsingYear(startDate),
				HttpStatus.OK);
	}

}
