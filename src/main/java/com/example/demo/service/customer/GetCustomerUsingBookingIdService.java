package com.example.demo.service.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetCustomerUsingBookingIdService {

	@Autowired
	private GetAllCustomerService getAllCustomerService;

	/**
	 * to Customer using BookingDetails.
	 *
	 * @param id - BookingDeatils id
	 * @return List of Customers
	 */
	public List<Customer> getCustomerUsingBookingId(Long id) {
		List<Customer> customer = getAllCustomerService.getAllCustomer().stream()
				.filter(e -> e.getBookingDetails().getBookingid() == id).collect(Collectors.toList());
		log.info("getting all customer details using bootkingDetails id");
		return customer;
	}
}
