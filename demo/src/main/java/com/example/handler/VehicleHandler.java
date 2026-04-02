package com.example.handler;

import com.example.exception.VehicleRenewalException;
import com.example.model.Vehicle;
import com.example.service.VehicleService;
import com.example.service.impl.VehicleServiceImpl;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class VehicleHandler implements HttpHandler {

    private final VehicleService vehicleService = new VehicleServiceImpl();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Allow frontend to call backend (CORS)
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        String method = exchange.getRequestMethod();
        String path   = exchange.getRequestURI().getPath();

        try {
            // OPTIONS preflight
            if (method.equalsIgnoreCase("OPTIONS")) {
                sendResponse(exchange, 200, "{}");

            // GET /api/vehicles — get all
            } else if (method.equals("GET") && path.equals("/api/vehicles")) {
                List<Vehicle> vehicles = vehicleService.getAllVehicles();
                sendResponse(exchange, 200, gson.toJson(vehicles));

            // GET /api/vehicles/{id} — get by id
            } else if (method.equals("GET") && path.startsWith("/api/vehicles/")) {
                int id = Integer.parseInt(path.replace("/api/vehicles/", ""));
                Vehicle v = vehicleService.getVehicleById(id);
                sendResponse(exchange, 200, gson.toJson(v));

            // POST /api/vehicles — add vehicle
            } else if (method.equals("POST") && path.equals("/api/vehicles")) {
                String body    = readBody(exchange);
                Vehicle vehicle = gson.fromJson(body, Vehicle.class);
                vehicleService.createVehicle(vehicle);
                sendResponse(exchange, 201, "{\"message\":\"Vehicle added\"}");

            // DELETE /api/vehicles/{mileage} — delete by mileage
            } else if (method.equals("DELETE") && path.startsWith("/api/vehicles/mileage/")) {
                int mileage = Integer.parseInt(path.replace("/api/vehicles/mileage/", ""));
                vehicleService.deleteVehicleByMileage(mileage);
                sendResponse(exchange, 200, "{\"message\":\"Deleted\"}");

            } else {
                sendResponse(exchange, 404, "{\"error\":\"Not found\"}");
            }

        } catch (VehicleRenewalException e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private String readBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    private void sendResponse(HttpExchange exchange, int code, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}