package com.example.demo.service.bookingdetail;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BookingDetails;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.BookingDetailsRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class GetBookingDetailByIdService {

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	/**
     * to get Bookings details with Booking ID.
     *
     * @param id - Booking's ID
     * @return Booking Details 
     */
	public Optional<BookingDetails> getBookingDetailsById(Long id) {
		validate(id);
		Optional<BookingDetails> dbBookingDetails = bookingDetailsRepository.findById(id);
		if(dbBookingDetails.isPresent())
		  {  log.info("geting bookingDetail of a specifict booking Id");
			return bookingDetailsRepository.findById(id);}
		else {
			log.info("something went wrong");
			throw new ValidationException("FV001", "this booking id do not exist");
		}

	}

	private void validate(Long id) {
		if (id <= 0 || id != (Long) id) {
			throw new ValidationException("FV001", "bookingDetails id cannot be 0 or empty");
		}
		
	}
	
}
