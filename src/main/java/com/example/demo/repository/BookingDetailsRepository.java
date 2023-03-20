package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BookingDetails;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {
	
	List<BookingDetails> findAllByStartDateBetween(String startDate, String endDate);
	
	List<BookingDetails> findAllByStartDateBefore(String startDate);
	
	
}
