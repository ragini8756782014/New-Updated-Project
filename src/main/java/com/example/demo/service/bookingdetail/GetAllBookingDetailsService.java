package com.example.demo.service.bookingdetail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BookingDetails;
import com.example.demo.repository.BookingDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetAllBookingDetailsService {

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	
	/**
     * to get List of all Bookings details.
     *
     * @return List of BookingDetails
     */
	public List<BookingDetails> getAllBookingDetails() {
		log.info("getting all bookingDetails");
		return bookingDetailsRepository.findAll();
	}
}
