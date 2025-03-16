# Car Lease Management System

## Overview
The Car Lease Management System is a Spring Boot application designed to manage car leases. It provides RESTful APIs for user registration, car registration, lease management, and administrative functions.

## Features
- User registration and management
- Car registration and management
- Lease creation, termination, and history tracking
- Administrative functions for managing users, cars, and leases

## Running the Application Using Docker (.tar File)
- You can access the application by loading the provided .tar file into Docker and running the container. Follow these steps:

1. Load the Docker Image
   sh
   Copy
   Edit
   docker load -i car-lease-app.tar
2. Run the Container
   sh
   Copy
   Edit
   docker run -p 8080:8080 --name car-lease-container car-lease-app
3. Access the Application
   API Base URL: http://localhost:8080
   H2 Database Console: http://localhost:8080/h2-console
   JDBC URL: jdbc:h2:mem:trimblecars
   Username: sa
   Password: (leave empty)
## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Gradle
- Mockito (for testing)
- Karate (for API testing)

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle 7.5 or higher

### Installation
1. Clone the repository:
    ```sh
    git clone repo-url
    cd car-lease-management
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```

3. Run the application:
    ```sh
    ./gradlew bootRun
    ```

### Running Tests
To run the unit tests and API tests, use the following command:
```sh
./gradlew test

API Endpoints

User API
POST /api/user/register: Register a new user
GET /api/user/get: Get user details by email
Car API

POST /api/car/register: Register a new car
GET /api/car/getCars: Get available cars
Lease API

POST /api/lease/create: Create a new lease
POST /api/lease/end/{leaseId}: End a lease
GET /api/lease/history/{customerId}: Get lease history for a customer
GET /api/lease/car-history/{ownerId}: Get lease history for a car owner
Admin API

GET /api/admin/users: Get all users
DELETE /api/admin/remove-user/{userId}: Remove a user
POST /api/admin/cars/enroll: Register a new car
GET /api/admin/cars: Get all cars
GET /api/admin/cars/status/{carId}: Get car status
DELETE /api/admin/remove-car/{carId}: Remove a car
GET /api/admin/leases: Get all leases
POST /api/admin/leases/start: Start a new lease
POST /api/admin/leases/end/{leaseId}: End a lease
GET /api/admin/leases/owner-history/{ownerId}: Get lease history for an owner
GET /api/admin/leases/customer-history/{customerId}: Get lease history for a customer
Additional Resources

Spring Boot Reference Documentation
Spring Data JPA Documentation
Gradle Documentation