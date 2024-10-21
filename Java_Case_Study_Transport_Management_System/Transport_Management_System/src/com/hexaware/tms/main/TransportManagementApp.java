package com.hexaware.tms.main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.hexaware.tms.dao.TransportManagementServiceImpl;
import com.hexaware.tms.entity.Bookings;
import com.hexaware.tms.entity.Vehicles;
import com.hexaware.tms.myexceptions.BookingNotFoundException;
import com.hexaware.tms.myexceptions.VehicleNotFoundException;
import com.hexaware.tms.services.ITransportManagementService; 

public class TransportManagementApp  {
	
	private static ITransportManagementService transportService;
	static Scanner scanner = new Scanner(System.in);
	
    public static void main(String[] args) throws BookingNotFoundException, VehicleNotFoundException {
    	
    	try {
    		transportService = new TransportManagementServiceImpl();
        } catch (SQLException e) {
            System.err.println("Failed to initialize transport service: " + e.getMessage());
            return;
        }

        
        int choice;

        do {
            System.out.println("\n--- Transport Management System ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Update Vehicle");
            System.out.println("3. Delete Vehicle");
            System.out.println("4. Schedule Trip");
            System.out.println("5. Cancel Trip");
            System.out.println("6. Book Trip");
            System.out.println("7. Cancel Booking");
            System.out.println("8. Allocate Driver");
            System.out.println("9. Deallocate Driver");
            System.out.println("10. Get Bookings by Passenger");
            System.out.println("11. Get Bookings by Trip");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    updateVehicle();
                    break;
                case 3:
                    deleteVehicle();
                    break;
                case 4:
                    scheduleTrip();
                    break;
                case 5:
                    cancelTrip();
                    break;
                case 6:
                    bookTrip();
                    break;
                case 7:
                    cancelBookings();
                    break;
                case 8:
                    allocateDriver();
                    break;
                case 9:
                    deallocateDriver();
                    break;
                case 10:
                    getBookingsByPassenger();
                    break;
                case 11:
                    getBookingsByTrip();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void addVehicle() {
        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();
        System.out.print("Enter vehicle capacity: ");
        double capacity = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();
        System.out.print("Enter vehicle status: ");
        String status = scanner.nextLine();

        Vehicles vehicle = new Vehicles(model, capacity, type, status);
        boolean isAdded = transportService.addVehicle(vehicle);
        System.out.println(isAdded ? "Vehicle added successfully!" : "Failed to add vehicle.");
    }

    private static void updateVehicle() throws VehicleNotFoundException {
    	System.out.print("Enter vehicle ID: ");
    	int vehicleId = scanner.nextInt();
    	scanner.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();
        System.out.print("Enter vehicle capacity: ");
        double capacity = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();
        System.out.print("Enter vehicle status: ");
        String status = scanner.nextLine();
        Vehicles vehicle = new Vehicles(vehicleId, model, capacity, type, status);
        boolean isUpdated = transportService.updateVehicle(vehicle);
        System.out.println(isUpdated ? "Vehicle updated successfully!" : "Failed to update vehicle.");
    }

    private static void deleteVehicle() {
        System.out.print("Enter vehicle ID to delete: ");
        int vehicleId = scanner.nextInt();
        boolean isDeleted = transportService.deleteVehicle(vehicleId);
        System.out.println(isDeleted ? "Vehicle deleted successfully!" : "Failed to delete vehicle.");
    }

    private static void scheduleTrip() {
        System.out.print("Enter vehicle ID: ");
        int vehicleId = scanner.nextInt();
        System.out.print("Enter route ID: ");
        int routeId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter departure date (YYYY-MM-DD HH:MM:SS): ");
        String departureDate = scanner.nextLine();
        System.out.print("Enter arrival date (YYYY-MM-DD HH:MM:SS): ");
        String arrivalDate = scanner.nextLine();

        boolean isScheduled = transportService.scheduleTrip(vehicleId, routeId, departureDate, arrivalDate);
        System.out.println(isScheduled ? "Trip scheduled successfully!" : "Failed to schedule trip.");
    }

    private static void cancelTrip() throws BookingNotFoundException {
        System.out.print("Enter trip ID to cancel: ");
        int tripId = scanner.nextInt();
        boolean isCancelled = transportService.cancelTrip(tripId);
        System.out.println(isCancelled ? "Trip cancelled successfully!" : "Failed to cancel trip.");
    }

    private static void bookTrip() throws BookingNotFoundException {
        System.out.print("Enter trip ID: ");
        int tripId = scanner.nextInt();
        System.out.print("Enter passenger ID: ");
        int passengerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter booking date (YYYY-MM-DD HH:MM:SS): ");
        String bookingDate = scanner.nextLine();

        boolean isBooked = transportService.bookTrip(tripId, passengerId, bookingDate);
        System.out.println(isBooked ? "Trip booked successfully!" : "Failed to book trip.");
    }

    private static void cancelBookings() throws BookingNotFoundException {
        System.out.print("Enter booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        boolean isCancelled = transportService.cancelBooking(bookingId);
        System.out.println(isCancelled ? "Booking cancelled successfully!" : "Failed to cancel booking.");
    }

    private static void allocateDriver() {
        System.out.print("Enter trip ID: ");
        int tripId = scanner.nextInt();
        System.out.print("Enter driver ID: ");
        int driverId = scanner.nextInt();

        boolean isAllocated = transportService.allocateDriver(tripId, driverId);
        System.out.println(isAllocated ? "Driver allocated successfully!" : "Failed to allocate driver.");
    }

    private static void deallocateDriver() {
        System.out.print("Enter trip ID: ");
        int tripId = scanner.nextInt();

        boolean isDeallocated = transportService.deallocateDriver(tripId);
        System.out.println(isDeallocated ? "Driver deallocated successfully!" : "Failed to deallocate driver.");
    }
    
 

    private static void getBookingsByPassenger() {
        System.out.print("Enter passenger ID: ");
        int passengerId = scanner.nextInt();
        List<Bookings> bookings = transportService.getBookingsByPassenger(passengerId);

        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No bookings found for this passenger.");
        } else {
            for (Bookings booking : bookings) {
                System.out.println("Booking ID: " + booking.getBookingID() + 
                                   ", Trip ID: " + booking.getTripID() + 
                                   ", Booking Date: " + booking.getBookingDate() + 
                                   ", Status: " + booking.getStatus());
            }
        }
    }

    private static void getBookingsByTrip() {
        System.out.print("Enter trip ID: ");
        int tripId = scanner.nextInt();
        List<Bookings> bookings = transportService.getBookingsByTrip(tripId);

        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No bookings found for this trip.");
        } else {
            for (Bookings booking : bookings) {
                System.out.println("Booking ID: " + booking.getBookingID() + 
                                   ", Passenger ID: " + booking.getPassengerID() + 
                                   ", Booking Date: " + booking.getBookingDate() + 
                                   ", Status: " + booking.getStatus());
            }
        }
    }
 
}
