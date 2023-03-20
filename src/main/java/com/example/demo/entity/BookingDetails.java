package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_details")
public class BookingDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bookingid;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private String startDate;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private String endDate;

	private String mode_of_booking;

	private String mode_of_payment;

	@OneToMany(mappedBy = "bookingDetails", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Customer> customer;

	private String category_type;

	private int no_of_rooms;

}
