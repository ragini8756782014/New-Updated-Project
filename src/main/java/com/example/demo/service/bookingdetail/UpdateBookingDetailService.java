package com.example.demo.service.bookingdetail;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.demo.classes.BookingDetailsReturn;
import com.example.demo.classes.RoomReturn;
import com.example.demo.entity.BookingDetails;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.BookingDetailsRepository;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.customer.UpdateCustomerService;
import com.example.demo.service.room.FindRoomNoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UpdateBookingDetailService {
	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;
	@Autowired
	private UpdateCustomerService updateCustomerService;
	@Autowired
	private FindRoomNoService findRoomNoService;

	/**
     * to update Booking Details.
     *
     * @param bookingDetails - Booking's Details
     * @return List of booked rooms and booking id
     */
	public BookingDetailsReturn updateBookingDetails(BookingDetails bookingDetails) {
		Optional<BookingDetails> dbBookingDetails = bookingDetailsRepository.findById(bookingDetails.getBookingid());

		List<RoomReturn> roomReturn = findRoomNoService.findRoomNo(bookingDetails.getCategory_type(),
				bookingDetails.getNo_of_rooms());
		if (roomReturn.isEmpty()) {
			log.info("something went wrong");
			throw new ValidationException("FV001", "We do not have this category room empty");
		}
		updateCustomerService.updateCustomer(bookingDetails.getCustomer(), roomReturn);
		if (dbBookingDetails.isPresent()) {
			log.info("bookingDetails are updated");
			bookingDetailsRepository.save(bookingDetails);
		}
		return createEntity(roomReturn,	bookingDetails);
	}

	/**
     * to get List of rooms booked .
     *
     *@param roomReturn - Rooms Booked
     *@param bookingDetails - Booking details 
     * @return List of all the rooms Booked as well as total Price with Booking id
     */
	public BookingDetailsReturn createEntity(List<RoomReturn> roomReturn, BookingDetails bd) {
		BookingDetailsReturn br = new BookingDetailsReturn();
		br.setBookingId(bd.getBookingid());
		br.setRoomReturn(roomReturn);
		br.setTotalPayment(getTotalPayment(roomReturn, bd.getNo_of_rooms(), bd.getStartDate(), bd.getEndDate()));
		return br;
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

	/**
     * to update Booking Details.
     *
     * @param id - Booking's Id
     * @param  fields - bookingDetails (key value pair)
     * @return response 
     */
	public BaseResponse updateBookingDetailsUsingPatch(Long id, Map<String, Object> fields) {
		Optional<BookingDetails> dbBookingDetails = bookingDetailsRepository.findById(id);
		BaseResponse baseresponse = new BaseResponse();
		if (dbBookingDetails.isPresent()) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(BookingDetails.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, dbBookingDetails, value);
			});
			bookingDetailsRepository.save(dbBookingDetails.get());
			baseresponse.setStatus(HttpStatus.ACCEPTED.value());
			baseresponse.setMessage("Your bookingDetail is updated");
			return baseresponse;
		}
		baseresponse.setStatus(HttpStatus.BAD_REQUEST.value());
		baseresponse.setMessage("this id do not exist ");
		return baseresponse;
	}

}
