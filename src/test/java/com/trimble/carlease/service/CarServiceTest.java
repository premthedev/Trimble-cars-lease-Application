package com.trimble.carlease.service;

import com.trimble.carlease.dto.CarRequest;
import com.trimble.carlease.model.Car;
import com.trimble.carlease.model.CarStatus;
import com.trimble.carlease.model.User;
import com.trimble.carlease.model.UserRole;
import com.trimble.carlease.repository.CarRepository;
import com.trimble.carlease.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CarService carService;

    private CarRequest carRequest;
    private User owner;
    private Car car;

    @BeforeEach
    void setUp() {
        carRequest = new CarRequest();
        carRequest.setOwnerId(UUID.randomUUID());
        carRequest.setMake("Toyota");
        carRequest.setModel("Camry");
        carRequest.setYear(2020);

        owner = new User();
        owner.setId(carRequest.getOwnerId());
        owner.setRole(UserRole.OWNER);

        car = new Car();
        car.setId(UUID.randomUUID());
        car.setOwner(owner);
        car.setMake(carRequest.getMake());
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setStatus(CarStatus.IDLE);
    }

    @Test
    void testRegisterCar() {
        when(userRepository.findById(carRequest.getOwnerId())).thenReturn(Optional.of(owner));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car registeredCar = carService.registerCar(carRequest);

        assertEquals(car.getId(), registeredCar.getId());
        verify(userRepository, times(1)).findById(carRequest.getOwnerId());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testRegisterCar_UserNotFound() {
        when(userRepository.findById(carRequest.getOwnerId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> carService.registerCar(carRequest));
        verify(userRepository, times(1)).findById(carRequest.getOwnerId());
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void testGetAvailableCars() {
        when(carRepository.findByStatus(CarStatus.IDLE)).thenReturn(Arrays.asList(car));

        List<Car> availableCars = carService.getAvailableCars();

        assertEquals(1, availableCars.size());
        assertEquals(car.getId(), availableCars.get(0).getId());
        verify(carRepository, times(1)).findByStatus(CarStatus.IDLE);
    }

    @Test
    void testGetCarStatus() {
        UUID carId = car.getId();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        String status = carService.getCarStatus(carId);

        assertEquals(CarStatus.IDLE.name(), status);
        verify(carRepository, times(1)).findById(carId);
    }
}