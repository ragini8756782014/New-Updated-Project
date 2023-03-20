package com.example.demo.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetAllCustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * to all Customers .
	 *
	 * @return List of Customers
	 */
	public List<Customer> getAllCustomer() {
		log.info("geting all customer details");
		return customerRepository.findAll();
	}

}
