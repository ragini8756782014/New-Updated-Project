package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Rooms {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long room_id;

	private int room_number;

	private int occupancy;

	private boolean availability;

	private int price_per_day;

	private boolean is_checked_in;

	private boolean is_checked_out;

	private String category_type;

	private int price_per_hours;

}
