package com.hexaware.tms.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hexaware.tms.dao.TransportManagementServiceImpl;

class BookingTest {

    private Connection connection;
    private TransportManagementServiceImpl tripService;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/transport_management_system", "root", "root");
        tripService = new TransportManagementServiceImpl();
    }

    // Test for successful trip booking.
    @Test
    public void testBookTrip_Success() throws SQLException {
        int tripId = 1;
        int passengerId = 1;
        String bookingDate = "2024-10-20";
        boolean result = tripService.bookTrip(tripId, passengerId, bookingDate);
        assertTrue(result, "The booking should be successful.");
    }

    @Test
    public void testBookTrip_Failure() throws SQLException {
        connection.close();
        boolean result = tripService.bookTrip(100, 99, "2024-10-21");
        assertFalse(result, "The booking should fail.");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
