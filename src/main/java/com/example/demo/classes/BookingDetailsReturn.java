package com.example.demo.classes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDetailsReturn {

	private Long bookingId;
	private List<RoomReturn> roomReturn;
	private int totalPayment;

}
