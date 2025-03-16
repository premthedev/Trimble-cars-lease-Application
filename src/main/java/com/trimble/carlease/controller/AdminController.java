package com.trimble.carlease.controller;

import com.trimble.carlease.dto.CarRequest;
import com.trimble.carlease.dto.LeaseRequest;
import com.trimble.carlease.model.Car;
import com.trimble.carlease.model.Lease;
import com.trimble.carlease.model.User;
import com.trimble.carlease.service.AdminService;
import com.trimble.carlease.service.CarService;
import com.trimble.carlease.service.LeaseService;
import com.trimble.carlease.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private CarService carService;

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        if (adminService.getAllUsers().isEmpty()) {
            logger.info("No users found");
        }
        return adminService.getAllUsers();
    }

    @DeleteMapping("/remove-user/{userId}")
    public void removeUser(@PathVariable UUID userId) {
        if (adminService.getAllUsers().isEmpty()) {
            logger.info("No users found");
        }
        logger.info("Removing user with ID: {}", userId);
        adminService.removeUser(userId);
    }

    @PostMapping("/cars/enroll")
    public Car registerCar(@RequestBody CarRequest carRequest) {
        logger.info("Registering car with details: {}", carRequest);
        return carService.registerCar(carRequest);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        logger.info("Fetching all cars");
        if (adminService.getAllCars().isEmpty()) {
            logger.info("No cars found");
        }
        return adminService.getAllCars();
    }

    @GetMapping("/cars/status/{carId}")
    public String getCarStatus(@PathVariable UUID carId) {
        logger.info("Fetching status for car with ID: {}", carId);
        return carService.getCarStatus(carId);
    }

    @DeleteMapping("/remove-car/{carId}")
    public void removeCar(@PathVariable UUID carId) {
        if (adminService.getAllCars().isEmpty()) {
            logger.info("No cars found");
        }
        logger.info("Removing car with ID: {}", carId);
        adminService.removeCar(carId);
    }

    @GetMapping("/leases")
    public List<Lease> getAllLeases() {
        logger.info("Fetching all leases");
        return adminService.getAllLeases();
    }

    @PostMapping("/leases/start")
    public Lease startLease(@RequestBody LeaseRequest request) {
        logger.info("Starting lease with details: {}", request);
        return leaseService.startLease(request);
    }

    @PostMapping("/leases/end/{leaseId}")
    public void endLease(@PathVariable UUID leaseId) {
        logger.info("Ending lease with ID: {}", leaseId);
        leaseService.endLease(leaseId);
    }

    @GetMapping("/leases/owner-history/{ownerId}")
    public List<Lease> getOwnerLeaseHistory(@PathVariable UUID ownerId) {
        logger.info("Fetching lease history for owner with ID: {}", ownerId);
        return leaseService.getOwnerLeaseHistory(ownerId);
    }

    @GetMapping("/leases/customer-history/{customerId}")
    public List<Lease> getCustomerLeaseHistory(@PathVariable UUID customerId) {
        logger.info("Fetching lease history for customer with ID: {}", customerId);
        return leaseService.getLeaseHistory(customerId);
    }
}