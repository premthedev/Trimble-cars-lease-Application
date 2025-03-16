package com.trimble.carlease.service;

import com.trimble.carlease.dto.LeaseRequest;
import com.trimble.carlease.model.Car;
import com.trimble.carlease.model.CarStatus;
import com.trimble.carlease.model.Lease;
import com.trimble.carlease.model.User;
import com.trimble.carlease.repository.CarRepository;
import com.trimble.carlease.repository.LeaseRepository;
import com.trimble.carlease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;

    public Lease startLease(LeaseRequest leaseRequest) {
        User customer = userRepository.findById(leaseRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Lease> activeLeases = leaseRepository.findByCustomer(customer);
        if (activeLeases.size() > 2) {
            throw new RuntimeException("User already has an active lease");
        }

        Car car = carRepository.findById(leaseRequest.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (car.getStatus() != CarStatus.IDLE) {
            throw new RuntimeException("No available cars to lease");
        }

        car.setStatus(CarStatus.ON_LEASE);
        carRepository.save(car);

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStartDate(LocalDateTime.now());

        return leaseRepository.save(lease);
    }

    public Lease endLease(UUID leaseId) {
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        lease.setEndDate(LocalDateTime.now());

        Car car = lease.getCar();
        car.setStatus(CarStatus.IDLE);
        carRepository.save(car);

        return leaseRepository.save(lease);
    }

    public List<Lease> getLeaseHistory(UUID customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return leaseRepository.findByCustomer(customer);
    }

    public List<Lease> getOwnerLeaseHistory(UUID ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return leaseRepository.findByCarOwner(owner);
    }
}