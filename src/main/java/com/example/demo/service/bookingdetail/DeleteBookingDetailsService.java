package com.example.demo.service.bookingdetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BookingDetails;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Rooms;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.BookingDetailsRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoomsRepository;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.room.GetAllRoomService;
import com.example.demo.service.room.UpdateRoomService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeleteBookingDetailsService {

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	@Autowired
	private GetAllRoomService getAllRoomService;

	@Autowired
	private CustomerRepository customerRepository;



	/**
     * to delete Booking details .
     *
     *@param id - Booking's Id
     *@return delete all customers and booking details 
     */
	public BaseResponse deleteBookingDetails(Long id) {
		validate(id);
		BaseResponse baseResponse = new BaseResponse();
		Optional<BookingDetails> dbBookingDetails = bookingDetailsRepository.findById(id);
		if (dbBookingDetails.isPresent()) {
			customerRepository.deleteByBookingId(id);
			deleteOccupancy(id);
			bookingDetailsRepository.deleteById(id);
			log.info("Booking Details are deleted");
			baseResponse.setStatus(HttpStatus.ACCEPTED.value());

			baseResponse.setMessage("your BookingDetail has been successfully deleted");
			return baseResponse;
		}
		log.info("bookingDetails Id is incorrect or do not found in database");
		baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		baseResponse.setMessage("your id is incorrect");
		return baseResponse;

	}

	private void deleteOccupancy(Long id) {

		List<Customer> customerList = getDeletedRoomsNo(id);
		List<Integer> i = new ArrayList<>();
		for (Customer customer : customerList) {
			i.add(customer.getRoom_no());
		}
		List<Rooms> rooms = getAllRoomService.getRoom();
		for (Integer roomNo : i) {
			for (Rooms r : rooms) {
				if (r.isAvailability() != true) {
					if (r.getRoom_number() == roomNo) {
						r.setAvailability(true);
						r.set_checked_in(false);
						r.set_checked_out(true);
						break;
					}
				}
			}
		}
	}
 
	
	/**
     * to get customers list having same id .
     *
     *@param id - Booking's Id
     *@return List of all Customers with Booking Id 
     */
	private List<Customer> getDeletedRoomsNo(long id) {
		List<Customer> customer = customerRepository.findAll().stream()
				.filter(e -> (e.getBookingDetails().getBookingid() == id)).collect(Collectors.toList());
		return customer;
	}

	private void validate(Long id) {
		if (id <= 0 || id != (Long) id) {
			throw new ValidationException("FV001", "bookingDetails id cannot be 0 or empty");
		}
	}
}
