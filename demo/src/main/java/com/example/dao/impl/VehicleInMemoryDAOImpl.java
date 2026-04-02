package com.example.dao.impl;

import com.example.dao.VehicleInMemoryDAO;
import com.example.exception.VehicleRenewalException;
import com.example.model.Vehicle;
 
import java.util.ArrayList;
import java.util.List;
 
public class VehicleInMemoryDAOImpl implements VehicleInMemoryDAO {
 
    private List<Vehicle> vehicleDatabase = new ArrayList<>();
 
    @Override
    public void createVehicle(Vehicle vehicle) {
        vehicleDatabase.add(vehicle);
    }
 
    @Override
    public Vehicle getVehicleById(int vehicleId) throws VehicleRenewalException {
        for (Vehicle v : vehicleDatabase) {
            if (v.getVehicleId() == vehicleId) {
                return v;
            }
        }
        return null;
    }
 
    // Returns updated Vehicle so test can call toString() on result
    @Override
    public Vehicle updateVehicleDetails(int vehicleId, Vehicle updatedVehicle) {
        for (Vehicle v : vehicleDatabase) {
            if (v.getVehicleId() == vehicleId) {
                v.setMake(updatedVehicle.getMake());
                v.setModel(updatedVehicle.getModel());
                try {
                    v.setYear(updatedVehicle.getYear());
                } catch (VehicleRenewalException e) {
                    v.setStatus("Renewal Required");
                }
                v.setColor(updatedVehicle.getColor());
                v.setOwner(updatedVehicle.getOwner());
                v.setLicensePlate(updatedVehicle.getLicensePlate());
                v.setMileage(updatedVehicle.getMileage());
                v.setStatus(updatedVehicle.getStatus());
                return v;
            }
        }
        return null;
    }
 
    @Override
    public void deleteVehicleByMileage(int mileageThreshold) {
        vehicleDatabase.removeIf(v -> v.getMileage() < mileageThreshold);
    }
 
    @Override
    public List<Vehicle> getVehiclesByMakeOrModel(String make, String model) {
        List<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle v : vehicleDatabase) {
            if (v.getMake().equalsIgnoreCase(make) ||
                v.getModel().equalsIgnoreCase(model)) {
                matchingVehicles.add(v);
            }
        }
        return matchingVehicles;
    }
 
    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleDatabase;
    }
}
