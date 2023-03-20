package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Customer;

import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


     @Transactional
     @Modifying
	@Query(value="Delete from customers  where bookingid=:bookingid",nativeQuery = true)
	public void deleteByBookingId(@Param ("bookingid") Long bookingid );
	
	
}
