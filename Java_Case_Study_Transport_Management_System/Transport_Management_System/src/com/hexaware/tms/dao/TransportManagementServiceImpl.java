package com.hexaware.tms.dao;

import com.hexaware.tms.entity.Bookings;
import com.hexaware.tms.entity.Vehicles;
import com.hexaware.tms.myexceptions.BookingNotFoundException;
import com.hexaware.tms.myexceptions.VehicleNotFoundException;
import com.hexaware.tms.services.TransportManagementService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hexaware.tms.util.DBConnection;

public class TransportManagementServiceImpl implements TransportManagementService {
	
	private Connection connection = null;
	
	Scanner sc = new Scanner(System.in);
	
	public TransportManagementServiceImpl() throws SQLException{
		connection = DBConnection.getConnection();
	}

	@Override
	public boolean addVehicle(Vehicles vehicle) {
		String query = "INSERT INTO VEHICLES (MODEL, CAPACITY, TYPE, STATUS) VALUES (?, ?, ?, ?);";
		try(PreparedStatement ps = connection.prepareStatement(query);){
			ps.setString(1, vehicle.getModel());
			ps.setDouble(2, vehicle.getCapacity());
			ps.setString(3, vehicle.getType());
			ps.setString(4, vehicle.getStatus());
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean updateVehicle(Vehicles vehicle) throws VehicleNotFoundException {
		String query = "UPDATE VEHICLES SET MODEL=?, CAPACITY=?, TYPE=?, STATUS=? WHERE VEHICLEID=?";
		try(PreparedStatement ps = connection.prepareStatement(query);) {
			ps.setString(1, vehicle.getModel());
	        ps.setDouble(2, vehicle.getCapacity());
	        ps.setString(3, vehicle.getType());
	        ps.setString(4, vehicle.getStatus());
	        ps.setInt(5, vehicle.getVehicleID());
	        int rowsAffected = ps.executeUpdate();
	        if (rowsAffected == 0) {
                throw new VehicleNotFoundException("Vehicle ID not found: " + vehicle.getVehicleID());
            }
	        return rowsAffected > 0;
		}catch(SQLException e) {
			System.err.println("Error: Unable to update vehicle - " + e.getMessage());
            return false;
		}
	}
	
	@Override
	public boolean deleteVehicle(int vehicleId) {
		String query = "DELETE FROM Vehicles WHERE VehicleID = ?";
	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, vehicleId);
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; 
	    }
	}
	
	@Override
	public boolean scheduleTrip(int vehicleId, int routeId, String departureDate, String arrivalDate) {
		String query = "INSERT INTO Trips (VehicleID, RouteID, DepartureDate, ArrivalDate, Status, TripType, MaxPassengers) VALUES (?, ?, ?, ?, 'Scheduled', 'Freight', 0)";
		// Modify MaxPassenger, TripType, Status using user input if needed.
		try (PreparedStatement ps = connection.prepareStatement(query)) {
     		ps.setInt(1, vehicleId); 
     		ps.setInt(2, routeId); 
     		ps.setString(3, departureDate); 
     		ps.setString(4, arrivalDate); 
     		int rowsAffected = ps.executeUpdate();
     		return rowsAffected > 0; 

		} catch (SQLException e) {
			e.printStackTrace(); 
			return false; 
		}
	}
	
	@Override
	public boolean cancelTrip(int tripId) throws BookingNotFoundException {
	    String query = "UPDATE Trips SET Status = 'Cancelled' WHERE TripID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, tripId);
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected == 0) {
                throw new BookingNotFoundException("Trip ID not found: " + tripId);
            }
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	    	System.err.println("Error: Unable to cancel the trip - " + e.getMessage());
            return false;
	    }
	}
	
	@Override
	public boolean bookTrip(int tripId, int passengerId, String bookingDate) {
	    String query = "INSERT INTO Bookings (TripID, PassengerID, BookingDate, Status) VALUES (?, ?, ?, 'Confirmed')";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, tripId);
	        preparedStatement.setInt(2, passengerId);
	        preparedStatement.setString(3, bookingDate);
	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean cancelBooking(int bookingId) throws BookingNotFoundException {
	    String query = "DELETE FROM Bookings WHERE BookingID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, bookingId);
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected == 0) {
                throw new BookingNotFoundException("Booking ID not found: " + bookingId);
            }
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean allocateDriver(int tripId, int driverId) {
	    String query = "UPDATE Trips SET DriverID = ? WHERE TripID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, driverId);
	        preparedStatement.setInt(2, tripId);
	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	@Override
	public boolean deallocateDriver(int tripId) {
	    String query = "UPDATE Trips SET DriverID = NULL WHERE TripID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, tripId);
	        int rowsAffected = preparedStatement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	    	System.out.println("No driver associated with trips in the database.");
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	@Override
	public List<Bookings> getBookingsByPassenger(int passengerId) {
	    List<Bookings> bookingsList = new ArrayList<>();
	    String query = "SELECT * FROM Bookings WHERE PassengerID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, passengerId);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            int bookingId = resultSet.getInt("BookingID");
	            int tripId = resultSet.getInt("TripID");
	            LocalDateTime bookingDate = resultSet.getTimestamp("BookingDate").toLocalDateTime();
	            String status = resultSet.getString("Status");
	            Bookings booking = new Bookings(bookingId, tripId, passengerId, bookingDate, status);
	            bookingsList.add(booking);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return bookingsList;
	}
	
	
	@Override
	public List<Bookings> getBookingsByTrip(int tripId) {
	    List<Bookings> bookingsList = new ArrayList<>();
	    String query = "SELECT * FROM Bookings WHERE TripID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, tripId);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            int bookingId = resultSet.getInt("BookingID");
	            int passengerId = resultSet.getInt("PassengerID");
	            LocalDateTime bookingDate = resultSet.getTimestamp("BookingDate").toLocalDateTime();
	            String status = resultSet.getString("Status");
	            Bookings booking = new Bookings(bookingId, tripId, passengerId, bookingDate, status);
	            bookingsList.add(booking);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return bookingsList;
	}


	
	
}
