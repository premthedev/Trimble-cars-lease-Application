package com.trimble.carlease.controller;

import com.trimble.carlease.dto.CarRequest;
import com.trimble.carlease.model.Car;
import com.trimble.carlease.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @PostMapping("/register")
    public Car registerCar(@RequestBody CarRequest carRequest){
        if (carRequest == null) {
            logger.error("Car details are missing");
            throw new IllegalArgumentException("Car details are missing");
        }
        logger.info("Registering car with details: {}", carRequest);

        return carService.registerCar(carRequest);
    }

    @GetMapping("/getCars")
    public List<Car> getAvailableCars(){
        logger.info("Fetching available cars");
        if (carService.getAvailableCars().isEmpty()) {
            logger.info("No cars available");
        }
        return carService.getAvailableCars();
    }
}