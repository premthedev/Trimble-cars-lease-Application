package com.trimble.carlease.repository;

import com.trimble.carlease.model.Car;
import com.trimble.carlease.model.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    List<Car> findByStatus(CarStatus status);
}
