package com.example.demo.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.classes.RoomReturn;
import com.example.demo.entity.Customer;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.CustomerRepository;

@Service
public class CreateCustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * to add room number to customer .
	 *
	 * @param customerList - Customer's List
	 * @param roomReturn   - Room booked to Customers
	 * @return void
	 */
	public void createCustomer(List<Customer> customerList, List<RoomReturn> roomReturn) {
		validate(customerList);
		int roomOccupancy = 0;
		for (RoomReturn roomList : roomReturn) {
			roomOccupancy = roomList.getOccupancy();
			break;
		}
		setCustomerRoomNo(customerList, roomReturn, roomOccupancy);
	}

	/**
	 * to add room number to customer .
	 *
	 * @param customerList - Customer's List
	 * @param roomReturn   - Room booked to Customers
	 * @param room_occupancy - no of customers in a single room
	 * @return void
	 */
	private void setCustomerRoomNo(List<Customer> customerList, List<RoomReturn> roomReturn, int room_occupancy) {
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
	private int getRoomIndex(int i, int roomOccupancy) {
		int room_index;
		switch (roomOccupancy) {
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
			throw new IllegalArgumentException("Unexpected value: " + roomOccupancy);
		}
		return room_index;
	}

	private void validate(List<Customer> list) {
		for (Customer c : list) {
			if (c.getAge() <= 0) {
				throw new ValidationException("FV001", " age cannot be 0/smaller then 0 or empty");
			} else if (c.getCustomer_full_name() == null || c.getCustomer_full_name().isBlank()
					|| c.getCustomer_full_name().isEmpty()) {
				throw new ValidationException("FV002", " customer name cannot be null or empty");
			} else if (c.getCustomer_address() == null || c.getCustomer_address().isBlank()
					|| c.getCustomer_address().isEmpty()) {
				throw new ValidationException("FV003", " customer address cannot be null or empty");
			} else {

			}
		}
	}

}
