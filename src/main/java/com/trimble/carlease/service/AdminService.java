package com.trimble.carlease.service;

import com.trimble.carlease.model.Car;
import com.trimble.carlease.model.Lease;
import com.trimble.carlease.model.User;
import com.trimble.carlease.repository.CarRepository;
import com.trimble.carlease.repository.LeaseRepository;
import com.trimble.carlease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }

    public void removeCar(UUID carId) {
        carRepository.deleteById(carId);
    }

    public void removeUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
