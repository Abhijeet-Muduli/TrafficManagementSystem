
CREATE DATABASE IF NOT EXISTS appdb;
USE appdb;

CREATE TABLE IF NOT EXISTS vehicles (
    vehicleId INT PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    color VARCHAR(30),
    owner VARCHAR(100),
    licensePlate VARCHAR(20),
    mileage INT,
    status VARCHAR(30)
);

INSERT INTO vehicles (vehicleId, make, model, year, color, owner, licensePlate, mileage, status)
VALUES (101, 'Toyota', 'Camry', 2022, 'Black', 'John Doe', 'ABC-1234', 5000, 'Active')
ON DUPLICATE KEY UPDATE vehicleId=vehicleId;