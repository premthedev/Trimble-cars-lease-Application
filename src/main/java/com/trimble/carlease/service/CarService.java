package com.trimble.carlease.service;

import com.trimble.carlease.dto.CarRequest;
import com.trimble.carlease.model.Car;
import com.trimble.carlease.model.CarStatus;
import com.trimble.carlease.model.User;
import com.trimble.carlease.model.UserRole;
import com.trimble.carlease.repository.CarRepository;
import com.trimble.carlease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;


    public Car registerCar(CarRequest carRequest) {
        User owner = userRepository.findById(carRequest.getOwnerId()).orElseThrow(() -> new RuntimeException("User not found"));

        if (owner.getRole() != UserRole.OWNER) {
            throw new RuntimeException("Only owners can register a car");
        }

        Car car = new Car();
        car.setOwner(owner);
        car.setMake(carRequest.getMake());
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setStatus(CarStatus.IDLE);
        return carRepository.save(car);
    }

    public List<Car> getAvailableCars() {
        return carRepository.findByStatus(CarStatus.IDLE);
    }

    public String getCarStatus(UUID carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        return car.getStatus().name();
    }
}
