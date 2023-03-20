package com.example.demo.service.bookingdetail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.classes.BookingDetailsReturn;
import com.example.demo.classes.RoomReturn;
import com.example.demo.entity.BookingDetails;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.BookingDetailsRepository;
import com.example.demo.service.customer.CreateCustomerService;
import com.example.demo.service.room.FindRoomNoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreateBookingDetailService {

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	@Autowired
	private CreateCustomerService createCustomerService;

	@Autowired
	private FindRoomNoService findRoomNoService;

	
	
	/**
     * to book the rooms .
     *
     *@param bookingDetails - Booking's Details
     *@return List of all the rooms Booked as well as total Price with Booking id
     */
	public BookingDetailsReturn createBookingDetail(BookingDetails bookingDetails) {
		validate(bookingDetails);
		List<RoomReturn> roomReturn = findRoomNoService.findRoomNo(bookingDetails.getCategory_type(),
				bookingDetails.getNo_of_rooms());
		if (roomReturn.isEmpty()) {
			log.info("Do not have empty rooms of this category");
			throw new ValidationException("FV001", "We do not have this category room empty");
		}
		createCustomerService.createCustomer(bookingDetails.getCustomer(), roomReturn);
		bookingDetailsRepository.save(bookingDetails);
		log.info("BookingDetails are saved successfully");
		return createEntity(roomReturn, bookingDetails);
	}
 
	/**
     * to get List of rooms booked .
     *
     *@param roomReturn - Rooms Booked
     *@param bookingDetails - Booking details 
     * @return List of all the rooms Booked as well as total Price with Booking id
     */
	public BookingDetailsReturn createEntity(List<RoomReturn> roomReturn, BookingDetails bookingDetails) {
		BookingDetailsReturn bookingDetailsReturn = new BookingDetailsReturn();
		bookingDetailsReturn.setBookingId(bookingDetails.getBookingid());
		bookingDetailsReturn.setRoomReturn(roomReturn);
		bookingDetailsReturn.setTotalPayment(getTotalPayment(roomReturn, bookingDetails.getNo_of_rooms(), bookingDetails.getStartDate(), bookingDetails.getEndDate()));
		return bookingDetailsReturn;
	}

	/**
     * to get price of rooms according to perday and hours .
     *
     *@param roomBooked - Rooms Booked
     *@param noOfRooms - No of rooms Customer want to book
     *@param startDate - Booking's startDate 
     *@param endDate  - Booking's endDate
     * @return total Booking Bill 
     */
	private int getTotalPayment(List<RoomReturn> roomBooked, int noOfRooms, String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		int total = 0;
		try {
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			int difference_In_Time = (int) (d2.getTime() - d1.getTime());
			int difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
			if (difference_In_Days == 0) {
				int difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
				if (difference_In_Hours < 24) {
					for (RoomReturn roomReturn : roomBooked) {
						total = roomReturn.getPricePerHour();
						break;
					}
					return total * noOfRooms * difference_In_Hours;

				} else {
					for (RoomReturn roomReturn : roomBooked) {
						total = roomReturn.getBillAmount();
						break;
					}
					return total * noOfRooms * 1;

				}
			} else {
				for (RoomReturn roomReturn : roomBooked) {
					total = roomReturn.getBillAmount();
					break;
				}
				return total * noOfRooms * difference_In_Days;
			}

		} catch (Exception e) {
			throw new ValidationException("FV001", " something went wrong in Date Time ");
		}
	}

	private void validate(BookingDetails bookingDetails) {
		if (bookingDetails.getCategory_type() == null || bookingDetails.getCategory_type().isBlank()
				|| bookingDetails.getCategory_type().isEmpty()) {
			throw new ValidationException("FV001", " category type cannot be null or empty");
		} else if (bookingDetails.getStartDate() == null || bookingDetails.getStartDate().isBlank()
				|| bookingDetails.getStartDate().isEmpty()
				|| bookingDetails.getStartDate().matches("dd-MM-yyyy HH:mm:ss")) {
			throw new ValidationException("FV002", " start date cannot be null or empty");
		} else if (bookingDetails.getEndDate()== null || bookingDetails.getEndDate().isBlank()
				|| bookingDetails.getEndDate().isEmpty()
				|| bookingDetails.getEndDate().matches("dd-MM-yyyy HH:mm:ss")) {
			throw new ValidationException("FV003", " end date cannot be null or empty");
		} else if (bookingDetails.getMode_of_booking() == null || bookingDetails.getMode_of_booking().isBlank()
				|| bookingDetails.getMode_of_booking().isEmpty()) {
			throw new ValidationException("FV004", " mode of booking cannot be null or empty");
		} else if (bookingDetails.getMode_of_payment() == null || bookingDetails.getMode_of_payment().isBlank()
				|| bookingDetails.getMode_of_payment().isEmpty()) {
			throw new ValidationException("FV005", " mode of payment cannot be null or empty");
		} else if (bookingDetails.getNo_of_rooms() <= 0) {
			throw new ValidationException("FV006", " no of rooms cannot be 0 or empty");
		} else {

		}
	}

}
