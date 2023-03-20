package com.example.demo.classes;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomReturn implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long roomId;

	private int roomNo;

	private int billAmount;

	private int pricePerHour;
	
	private int occupancy;
}
