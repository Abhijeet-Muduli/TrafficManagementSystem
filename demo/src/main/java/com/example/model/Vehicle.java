package com.example.model;

import com.example.exception.VehicleRenewalException;
 
public class Vehicle {
    private int vehicleId;
    private String make;
    private String model;
    private int year;
    private String color;
    private String owner;
    private String licensePlate;
    private int mileage;
    private String status;
 
    // Private no-arg constructor used by fromDatabase()
    private Vehicle() {}
 
    // Public constructor — throws for vehicles older than 15 years
    public Vehicle(int vehicleId, String make, String model, int year,
                   String color, String owner, String licensePlate,
                   int mileage, String status) throws VehicleRenewalException {
        this.vehicleId    = vehicleId;
        this.make         = make;
        this.model        = model;
        this.color        = color;
        this.owner        = owner;
        this.licensePlate = licensePlate;
        this.mileage      = mileage;
        this.status       = status;
        setYear(year);
    }
 
    // Static factory method used by VehicleDAOImpl — bypasses age check for DB reads
    public static Vehicle fromDatabase(int vehicleId, String make, String model,
                                       int year, String color, String owner,
                                       String licensePlate, int mileage, String status) {
        Vehicle v      = new Vehicle();
        v.vehicleId    = vehicleId;
        v.make         = make;
        v.model        = model;
        v.year         = year;
        v.color        = color;
        v.owner        = owner;
        v.licensePlate = licensePlate;
        v.mileage      = mileage;
        v.status       = status;
        return v;
    }
 
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }
 
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
 
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
 
    public int getYear() { return year; }
 
    // setYear throws VehicleRenewalException when vehicle is older than 15 years
    public void setYear(int year) throws VehicleRenewalException {
        this.year = year;
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        if ((currentYear - year) > 15) {
            this.status = "Renewal Required";
            throw new VehicleRenewalException(
                "This vehicle is too old and requires renewal or fitness test.");
        }
    }
 
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
 
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
 
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
 
    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
 
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
 
    @Override
    public String toString() {
        return "Vehicle [vehicleId=" + vehicleId +
               ", make=" + make +
               ", model=" + model +
               ", year=" + year +
               ", color=" + color +
               ", owner=" + owner +
               ", licensePlate=" + licensePlate +
               ", mileage=" + mileage +
               ", status=" + status + "]";
    }
}
