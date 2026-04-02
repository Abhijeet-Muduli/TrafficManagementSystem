package com.example;

import com.example.handler.VehicleHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) throws Exception {

        // Create HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // ── BACKEND API ROUTE ──
        // All /api/vehicles requests go to VehicleHandler
        server.createContext("/api/vehicles", new VehicleHandler());

        // ── FRONTEND FILE SERVER ──
        // Serves index.html, style.css, script.js from src/frontend/
        server.createContext("/", exchange -> {
            String uriPath = exchange.getRequestURI().getPath();

            // Default to index.html
            if (uriPath.equals("/")) uriPath = "/index.html";

            // Look for file in src/frontend folder
            Path filePath = Paths.get("src/frontend" + uriPath);

            if (Files.exists(filePath)) {
                byte[] bytes = Files.readAllBytes(filePath);

                // Set correct content type
                String contentType = "text/html";
                if (uriPath.endsWith(".css"))  contentType = "text/css";
                if (uriPath.endsWith(".js"))   contentType = "application/javascript";
                if (uriPath.endsWith(".json")) contentType = "application/json";

                exchange.getResponseHeaders().add("Content-Type", contentType);
                exchange.sendResponseHeaders(200, bytes.length);

                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();

            } else {
                // File not found
                String notFound = "404 - File Not Found";
                exchange.sendResponseHeaders(404, notFound.length());
                OutputStream os = exchange.getResponseBody();
                os.write(notFound.getBytes());
                os.close();
            }
        });

        // Start server
        server.start();

        System.out.println("=========================================");
        System.out.println("  Vehicle Management System - RUNNING");
        System.out.println("=========================================");
        System.out.println("  Frontend : http://localhost:8080");
        System.out.println("  API      : http://localhost:8080/api/vehicles");
        System.out.println("  Database : MySQL appdb @ port 3306");
        System.out.println("=========================================");
    }
}