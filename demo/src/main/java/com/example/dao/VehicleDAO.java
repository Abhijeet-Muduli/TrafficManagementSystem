package com.example.dao;

import com.example.exception.VehicleRenewalException;
import com.example.model.Vehicle;
import java.util.List;
 
public interface VehicleDAO {
    void createVehicle(Vehicle vehicle);
    Vehicle getVehicleById(int vehicleId) throws VehicleRenewalException;
    void updateVehicleDetails(int vehicleId, String make, String model,
                              int year, String color, String owner,
                              String licensePlate, int mileage, String status);
    void deleteVehicleByMileage(int mileageThreshold);
    List<Vehicle> getVehiclesByMakeOrModel(String make, String model) throws VehicleRenewalException;
    List<Vehicle> getAllVehicles() throws VehicleRenewalException;
}
 
