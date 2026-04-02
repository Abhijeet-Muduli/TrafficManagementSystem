package com.example.service.impl;

import com.example.dao.VehicleDAO;
import com.example.dao.impl.VehicleDAOImpl;
import com.example.exception.VehicleRenewalException;
import com.example.model.Vehicle;
import com.example.service.VehicleService;
 
import java.util.List;
 
public class VehicleServiceImpl implements VehicleService {
 
    private VehicleDAO vehicleDAO = new VehicleDAOImpl();
 
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
        vehicleDAO.updateVehicleDetails(vehicleId, vehicle.getMake(), vehicle.getModel(),
                vehicle.getYear(), vehicle.getColor(), vehicle.getOwner(),
                vehicle.getLicensePlate(), vehicle.getMileage(), vehicle.getStatus());
        return vehicleDAO.getVehicleById(vehicleId);
    }
 
    @Override
    public void deleteVehicleByMileage(int mileageThreshold) {
        vehicleDAO.deleteVehicleByMileage(mileageThreshold);
    }
 
    @Override
    public List<Vehicle> getVehiclesByMakeOrModel(String make, String model) throws VehicleRenewalException {
        return vehicleDAO.getVehiclesByMakeOrModel(make, model);
    }
 
    @Override
    public List<Vehicle> getAllVehicles() throws VehicleRenewalException {
        return vehicleDAO.getAllVehicles();
    }
}
