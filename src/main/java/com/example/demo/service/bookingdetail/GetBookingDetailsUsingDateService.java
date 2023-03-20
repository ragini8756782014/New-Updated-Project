package com.example.demo.service.bookingdetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BookingDetails;
import com.example.demo.repository.BookingDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetBookingDetailsUsingDateService {

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	@Autowired
	private GetAllBookingDetailsService getAllBookingDetailsService;
 
	/**
     * to get Bookings details between startDate and endDate.
     *
     * @param startDate - Booking's startDate
     * @param endDate -  Booking's EndDate
     * @return List Booking's Details
     */
	public List<BookingDetails> getBookingDetailsBetween(String startDate, String endDate) {
		log.info("getting all BookingDetails between two dates");
		return bookingDetailsRepository.findAllByStartDateBetween(startDate, endDate);
	}

	/**
     * to get all Bookings details before startDate .
     *
     * @param startDate - Booking's startDate
     * @return List Booking's Details
     */
	public List<BookingDetails> getBookingDetailsBeforeDate(String date) {
		log.info("geting all bookingDetail before a given date ");
		return bookingDetailsRepository.findAllByStartDateBefore(date);
	}

	/**
     * to get Bookings details of startDate year .
     *
     * @param startDate - Booking's startDate
     * @return List Booking's Details
     */
	public List<BookingDetails> getBookingDetailsUsingYear(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		System.out.println(date);
		Date current_date = format.parse(date);
		System.out.println(current_date);
		int year = current_date.getYear();
		List<BookingDetails> list = getAllBookingDetailsService.getAllBookingDetails().stream().filter(e -> {

			Date date_to_check;
			try {
				date_to_check = format.parse(e.getStartDate());
				return date_to_check.getYear() == year;
			} catch (ParseException e1) {

				e1.printStackTrace();
			}
			return false;
		}).collect(Collectors.toList());
        
		return list;
	}
}
