# EcoTrack AI

EcoTrack AI is a Smart Carbon Footprint Tracking and Sustainability Recommendation System built with Java Spring Boot and a sleek Glassmorphism Frontend.

## Features
- **User Authentication**: Secure JWT-based login and registration.
- **Activity Logging**: Track transportation and electricity footprints.
- **AI Recommendations**: Get sustainability suggestions based on logged activities.
- **PDF Reports**: Export your footprint summary and recommendations to PDF.
- **Premium UI**: Dark-themed, glassmorphism UI with responsive design.

## Prerequisites
- Java 17
- Maven 3.8+
- MySQL 8.0+

## Local Setup

### 1. Database Configuration
Ensure MySQL is running on `localhost:3306`.
Create the database:
```sql
CREATE DATABASE IF NOT EXISTS ecotrack_db;
```
The application uses `root` as the default username and password. If your credentials differ, update `src/main/resources/application.properties`.

### 2. Build and Run (Maven)
Navigate to the root directory and run:
```bash
mvn clean install
mvn spring-boot:run
```
The application will start on `http://localhost:8080`.

### 3. Docker Setup (Optional)
You can run the entire stack using Docker Compose:
```bash
docker-compose up --build
```

## Usage
1. Open your browser and navigate to `http://localhost:8080`.
2. Sign up for a new account or use the pre-loaded sample user:
   - **Username**: `john`
   - **Password**: `password`
3. Log in to access the Dashboard.
4. Add transportation and electricity logs to see your footprint increase.
5. Download your personalized PDF report.

## Architecture
- **Backend**: Spring Boot, Spring Security, Spring Data JPA, JWT, iText PDF.
- **Frontend**: HTML5, Vanilla CSS3 (Custom Glassmorphism), Vanilla JavaScript, Chart.js, Bootstrap 5.
- **Database**: MySQL.
