package com.example.dao.impl;

import com.example.config.JdbcUtils;
import com.example.dao.VehicleDAO;
import com.example.exception.VehicleRenewalException;
import com.example.model.Vehicle;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class VehicleDAOImpl implements VehicleDAO {
 
    @Override
    public void createVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (vehicleId,make,model,year,color,owner,licensePlate,mileage,status) VALUES(?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, vehicle.getVehicleId());
            ps.setString(2, vehicle.getMake());
            ps.setString(3, vehicle.getModel());
            ps.setInt(4, vehicle.getYear());
            ps.setString(5, vehicle.getColor());
            ps.setString(6, vehicle.getOwner());
            ps.setString(7, vehicle.getLicensePlate());
            ps.setInt(8, vehicle.getMileage());
            ps.setString(9, vehicle.getStatus());
            ps.executeUpdate();
            System.out.println("Vehicle inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting vehicle: " + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(conn);
        }
    }
 
    @Override
    public Vehicle getVehicleById(int vehicleId) throws VehicleRenewalException {
        String sql = "SELECT * FROM vehicles WHERE vehicleId=?";
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, vehicleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Vehicle v = mapResultSetToVehicle(rs);
                int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                if ((currentYear - v.getYear()) > 15) {
                    throw new VehicleRenewalException(
                        "This vehicle is too old and requires renewal or fitness test.");
                }
                return v;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving vehicle: " + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return null;
    }
 
    @Override
    public void updateVehicleDetails(int vehicleId, String make, String model,
                                     int year, String color, String owner,
                                     String licensePlate, int mileage, String status) {
        String sql = "UPDATE vehicles SET make=?,model=?,year=?,color=?,owner=?,licensePlate=?,mileage=?,status=? WHERE vehicleId=?";
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, make);
            ps.setString(2, model);
            ps.setInt(3, year);
            ps.setString(4, color);
            ps.setString(5, owner);
            ps.setString(6, licensePlate);
            ps.setInt(7, mileage);
            ps.setString(8, status);
            ps.setInt(9, vehicleId);
            ps.executeUpdate();
            System.out.println("Vehicle updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(conn);
        }
    }
 
    @Override
    public void deleteVehicleByMileage(int mileageThreshold) {
        String sql = "DELETE FROM vehicles WHERE mileage<?";
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, mileageThreshold);
            int rows = ps.executeUpdate();
            System.out.println(rows + " vehicle(s) deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting vehicles: " + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(conn);
        }
    }
 
    @Override
    public List<Vehicle> getVehiclesByMakeOrModel(String make, String model) throws VehicleRenewalException {
        String sql = "SELECT * FROM vehicles WHERE make=? OR model=?";
        List<Vehicle> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, make);
            ps.setString(2, model);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToVehicle(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vehicles: " + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return list;
    }
 
    @Override
    public List<Vehicle> getAllVehicles() throws VehicleRenewalException {
        String sql = "SELECT * FROM vehicles";
        List<Vehicle> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                list.add(mapResultSetToVehicle(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all vehicles: " + e.getMessage());
        } finally {
            JdbcUtils.closeConnection(conn);
        }
        return list;
    }
 
    // Uses fromDatabase() — no throws, avoids compilation errors
    private Vehicle mapResultSetToVehicle(ResultSet rs) throws SQLException {
        return Vehicle.fromDatabase(
            rs.getInt("vehicleId"),
            rs.getString("make"),
            rs.getString("model"),
            rs.getInt("year"),
            rs.getString("color"),
            rs.getString("owner"),
            rs.getString("licensePlate"),
            rs.getInt("mileage"),
            rs.getString("status")
        );
    }
}
