package com.hexaware.tms.services;

import java.util.List;

import com.hexaware.tms.entity.Bookings;
import com.hexaware.tms.entity.Vehicles;
import com.hexaware.tms.myexceptions.BookingNotFoundException;
import com.hexaware.tms.myexceptions.VehicleNotFoundException;

public interface TransportManagementService {

	public boolean addVehicle(Vehicles vehicle);
	
	public boolean updateVehicle(Vehicles vehicle) throws VehicleNotFoundException;
	
	public boolean deleteVehicle(int vehicleId);
	
	public boolean scheduleTrip(int vehicleId, int routeId, String departureDate,
			String arrivalDate);
	
	public boolean cancelTrip(int tripId) throws BookingNotFoundException;
	
	public boolean bookTrip(int tripId, int passengerId, String bookingDate);
	
	public boolean cancelBooking(int bookingId) throws BookingNotFoundException;
	
	public boolean allocateDriver(int tripId, int driverId);
	
	public boolean deallocateDriver(int tripId);
	
	public List<Bookings> getBookingsByPassenger(int passengerId);
	
	public List<Bookings> getBookingsByTrip(int tripId);
	
}
