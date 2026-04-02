package com.example.dao;

import com.example.model.Vehicle;
import com.example.exception.VehicleRenewalException;
import java.util.List;
 
public interface VehicleInMemoryDAO {
    void createVehicle(Vehicle vehicle);
    Vehicle getVehicleById(int vehicleId) throws VehicleRenewalException;
    Vehicle updateVehicleDetails(int vehicleId, Vehicle updatedVehicle);
    void deleteVehicleByMileage(int mileageThreshold);
    List<Vehicle> getVehiclesByMakeOrModel(String make, String model);
    List<Vehicle> getAllVehicles();
}
