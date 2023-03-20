package com.example.demo.service.customer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetCustomerByIdService {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * to get Customer using Id .
	 *
	 * @param id - Customers id
	 * @return Customer
	 */
	public Optional<Customer> getCustomerById(Long id) {
		validate(id);
		Optional<Customer> dbcustomer = customerRepository.findById(id);
		if(dbcustomer.isPresent())
		{    log.info("getting details of all customers using cstomer id");
			return customerRepository.findById(id);}
		else {
			 log.info("something went wrong");
			throw new ValidationException("FV001", "id is incorrect or do not exist");
		}

	}

	private void validate(Long id) {
		if (id <= 0 || id != (Long) id) {
			throw new ValidationException("FV001", "customer id cannot be null or empty");
		}
	}
}
