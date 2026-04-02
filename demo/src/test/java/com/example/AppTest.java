package com.example;

import com.example.exception.VehicleRenewalException;
import com.example.model.Vehicle;
import com.example.service.VehicleInMemoryService;
import com.example.service.impl.VehicleInMemoryServiceImpl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class AppTest {

    private VehicleInMemoryService vehicleService;

    @Before
    public void setUp() {
        vehicleService = new VehicleInMemoryServiceImpl();
    }

    // ── TEST 1: Create a valid vehicle ──
    @Test
    public void testCreateVehicle() throws VehicleRenewalException {
        Vehicle v = new Vehicle(1, "Toyota", "Camry", 2020,
                "White", "Alice", "ABC1234", 15000, "Active");
        vehicleService.createVehicle(v);

        Vehicle result = vehicleService.getVehicleById(1);
        assertNotNull("Vehicle should not be null", result);
        assertEquals("Toyota", result.getMake());
        assertEquals("Camry", result.getModel());
        assertEquals("Active", result.getStatus());
    }

    // ── TEST 2: Get vehicle by ID ──
    @Test
    public void testGetVehicleById() throws VehicleRenewalException {
        Vehicle v = new Vehicle(2, "Honda", "City", 2022,
                "Black", "Bob", "XYZ9999", 5000, "Active");
        vehicleService.createVehicle(v);

        Vehicle result = vehicleService.getVehicleById(2);
        assertNotNull(result);
        assertEquals(2, result.getVehicleId());
        assertEquals("Honda", result.getMake());
    }

    // ── TEST 3: Get all vehicles ──
    @Test
    public void testGetAllVehicles() throws VehicleRenewalException {
        vehicleService.createVehicle(new Vehicle(3, "Ford", "Focus", 2021,
                "Red", "Carol", "RED1111", 20000, "Active"));
        vehicleService.createVehicle(new Vehicle(4, "BMW", "X5", 2023,
                "Blue", "Dave", "BMW2222", 8000, "Active"));

        List<Vehicle> all = vehicleService.getAllVehicles();
        assertTrue("Should have at least 2 vehicles", all.size() >= 2);
    }

    // ── TEST 4: Old vehicle constructor throws VehicleRenewalException ──
    @Test(expected = VehicleRenewalException.class)
    public void testOldVehicleThrowsException() throws VehicleRenewalException {
        // 2005 is more than 15 years old — should throw
        new Vehicle(5, "Honda", "Civic", 2005,
                "Black", "Eve", "OLD1234", 80000, "Active");
    }

    // ── TEST 5: setYear throws and sets status for old vehicle ──
    @Test
    public void testSetYearSetsRenewalStatus() throws VehicleRenewalException {
        Vehicle v = new Vehicle(6, "Toyota", "Corolla", 2022,
                "White", "Frank", "NEW5678", 10000, "Active");
        try {
            v.setYear(2005); // old year — should throw
            fail("Expected VehicleRenewalException");
        } catch (VehicleRenewalException e) {
            assertEquals("Renewal Required", v.getStatus());
            assertTrue(e.getMessage().contains(
                "This vehicle is too old and requires renewal or fitness test."));
        }
    }

    // ── TEST 6: Delete vehicles by mileage ──
    @Test
    public void testDeleteVehicleByMileage() throws VehicleRenewalException {
        vehicleService.createVehicle(new Vehicle(7, "Suzuki", "Swift", 2021,
                "Green", "Grace", "DEL1234", 5000, "Active"));
        vehicleService.createVehicle(new Vehicle(8, "Hyundai", "i20", 2021,
                "Blue", "Henry", "DEL5678", 50000, "Active"));

        vehicleService.deleteVehicleByMileage(10000);

        List<Vehicle> all = vehicleService.getAllVehicles();
        boolean deleted = all.stream().noneMatch(v -> v.getVehicleId() == 7);
        assertTrue("Low mileage vehicle should be deleted", deleted);
    }

    // ── TEST 7: Search by make or model ──
    @Test
    public void testGetVehiclesByMakeOrModel() throws VehicleRenewalException {
        vehicleService.createVehicle(new Vehicle(9, "Tesla", "Model S", 2022,
                "White", "Ivan", "TES1234", 30000, "Active"));
        vehicleService.createVehicle(new Vehicle(10, "Tesla", "Model 3", 2023,
                "Red", "Jane", "TES5678", 20000, "Active"));

        List<Vehicle> result = vehicleService.getVehiclesByMakeOrModel("Tesla", "");
        assertTrue("Should find Tesla vehicles", result.size() >= 2);
    }

    // ── TEST 8: Vehicle toString contains all fields ──
    @Test
    public void testVehicleToString() throws VehicleRenewalException {
        Vehicle v = new Vehicle(11, "Kia", "Seltos", 2023,
                "Silver", "Karl", "KIA1234", 12000, "Active");
        String str = v.toString();

        assertTrue(str.contains("Kia"));
        assertTrue(str.contains("Seltos"));
        assertTrue(str.contains("2023"));
        assertTrue(str.contains("Active"));
    }
}