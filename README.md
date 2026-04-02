# 🚦 Traffic Management System

A full-stack Java application designed to manage vehicle registries, track mileage, and automatically flag vehicles requiring safety renewals based on their age.

## 🚀 Features
- **Vehicle Registry:** Add, view, and manage vehicle details (Make, Model, Year, Owner, etc.).
- **Automatic Status Logic:** Custom exception handling triggers a "Renewal Required" status for vehicles older than 15 years.
- **RESTful API:** Lightweight backend built with `com.sun.net.httpserver`.
- **Database Integration:** Persistent storage using **MySQL**.
- **Modern UI:** A clean, responsive dashboard for managing data in real-time.

---

## 🛠️ Tech Stack
- **Backend:** Java 17
- **Build Tool:** Maven
- **Database:** MySQL 8.0
- **Frontend:** HTML5, CSS3, JavaScript (Vanilla)
- **Library:** Google GSON (JSON Parsing)

---

## 📋 Prerequisites
Before running this project, ensure you have:
- **Java JDK 17** or higher installed.
- **Maven** installed.
- **MySQL Server** running.

---

## 🔧 Setup & Installation

### 1. Database Setup
Run the following SQL commands in your MySQL terminal or Workbench to prepare the database:

```sql
CREATE DATABASE appdb;
USE appdb;

CREATE TABLE vehicles (
    vehicleId INT PRIMARY KEY,
    make VARCHAR(50),
    model VARCHAR(50),
    year INT,
    color VARCHAR(30),
    owner VARCHAR(100),
    licensePlate VARCHAR(20),
    mileage INT,
    status VARCHAR(30)
);
---

### How to update this on your GitHub:
1. Save the file as `README.md`.
2. Run these commands in your terminal:
   ```powershell
   git add README.md
   git commit -m "Add project documentation (README)"
   git push
