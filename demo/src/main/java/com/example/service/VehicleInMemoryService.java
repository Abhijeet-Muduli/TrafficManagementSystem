package com.example.service;

import com.example.exception.VehicleRenewalException;
import com.example.model.Vehicle;
import java.util.List;
 
public interface VehicleInMemoryService {
    void createVehicle(Vehicle vehicle);
    Vehicle getVehicleById(int vehicleId) throws VehicleRenewalException;
    Vehicle updateVehicleDetails(int vehicleId, Vehicle vehicle) throws VehicleRenewalException;
    void deleteVehicleByMileage(int mileageThreshold);
    List<Vehicle> getVehiclesByMakeOrModel(String make, String model);
    List<Vehicle> getAllVehicles();
}
