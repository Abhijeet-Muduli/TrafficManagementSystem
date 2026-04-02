package com.example.service.impl;

import com.example.dao.VehicleInMemoryDAO;
import com.example.dao.impl.VehicleInMemoryDAOImpl;
import com.example.exception.VehicleRenewalException;
import com.example.model.Vehicle;
import com.example.service.VehicleInMemoryService;
 
import java.util.List;
 
public class VehicleInMemoryServiceImpl implements VehicleInMemoryService {
 
    private VehicleInMemoryDAO vehicleDAO = new VehicleInMemoryDAOImpl();
 
    @Override
    public void createVehicle(Vehicle vehicle) {
        vehicleDAO.createVehicle(vehicle);
    }
 
    @Override
    public Vehicle getVehicleById(int vehicleId) throws VehicleRenewalException {
        return vehicleDAO.getVehicleById(vehicleId);
    }
 
    @Override
    public Vehicle updateVehicleDetails(int vehicleId, Vehicle vehicle) throws VehicleRenewalException {
        return vehicleDAO.updateVehicleDetails(vehicleId, vehicle);
    }
 
    @Override
    public void deleteVehicleByMileage(int mileageThreshold) {
        vehicleDAO.deleteVehicleByMileage(mileageThreshold);
    }
    @Override
    public List<Vehicle> getVehiclesByMakeOrModel(String make, String model) {
        return vehicleDAO.getVehiclesByMakeOrModel(make, model);
    }
 
    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }
}

