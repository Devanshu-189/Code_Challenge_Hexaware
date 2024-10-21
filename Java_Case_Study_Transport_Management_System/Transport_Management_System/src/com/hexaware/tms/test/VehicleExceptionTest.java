package com.hexaware.tms.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import com.hexaware.tms.dao.TransportManagementServiceImpl;
import com.hexaware.tms.entity.Vehicles;
import com.hexaware.tms.myexceptions.VehicleNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehicleExceptionTest {

    private static Connection connection;
    private TransportManagementServiceImpl service;

    @BeforeAll
    public static void setUpDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/transport_management_system", "root", "root");
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO VEHICLES (VEHICLEID, MODEL, CAPACITY, TYPE, STATUS) VALUES (8, 'TestModel', 5, 'SUV', 'Available')")) {
            ps.executeUpdate();
        }
    }

    @AfterAll
    public static void ShutDownDatabase() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM VEHICLES WHERE VEHICLEID = 8;")) {
            ps.executeUpdate();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        service = new TransportManagementServiceImpl();
    }

    @Test
    public void testUpdateVehicle_Success() throws Exception {
        Vehicles vehicle = new Vehicles(1, "UpdatedModel", 7, "Truck", "Unavailable");
        boolean isUpdated = service.updateVehicle(vehicle);
        assertTrue(isUpdated, "Vehicle should be updated successfully.");
    }

    @Test
    public void testUpdateVehicle_VehicleNotFound() {
        Vehicles vehicle = new Vehicles(999, "NonExistentModel", 4, "Sedan", "Unavailable");
        VehicleNotFoundException exception = assertThrows(VehicleNotFoundException.class, () -> {
            service.updateVehicle(vehicle);
        });

        assertEquals("Vehicle ID not found: 999", exception.getMessage(), "Exception message should match when vehicle not found.");
    }
}
