package com.example.demo.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.classes.RoomReturn;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class UpdateCustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * to add room number to customer .
	 *
	 * @param customerList - Customer's List
	 * @param roomReturn   - Room booked to Customers
	 * @return void
	 */
	public void updateCustomer(List<Customer> list, List<RoomReturn> roomReturn) {
		int room_occupancy = 0;
		for (RoomReturn roomList : roomReturn) {
			room_occupancy = roomList.getOccupancy();
			break;
		}
		getCustomerIdFromList(list, roomReturn,room_occupancy);
	}

	/**
	 * to add room number to customer .
	 *
	 * @param customerList - Customer's List
	 * @param roomReturn   - Room booked to Customers
	 * @param room_occupancy - no of customers in a single room
	 * @return void
	 */
	private void getCustomerIdFromList(List<Customer> customerList, List<RoomReturn> roomReturn, int room_occupancy) {
		for (int i = 0; i < customerList.size(); i++) {
			int roomIndex = getRoomIndex(i, room_occupancy);
			Customer c = customerList.get(i);
			c.setRoom_no(roomReturn.get(roomIndex).getRoomNo());
			customerRepository.save(c);
		}
	}

	/**
	 * to get index for room .
	 *
	 * @param i - iterate customer
	 * @param roomOccupancy - no of customers in a single room
	 * @return room no
	 */
	private int getRoomIndex(int i, int room_occupancy) {
		int room_index;
		switch (room_occupancy) {
		case 1:
			room_index = i;
			break;
		case 2:
			room_index = i / 2;
			break;
		case 3:
			room_index = i / 3;
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + room_occupancy);
		}
		return room_index;
	}

}
