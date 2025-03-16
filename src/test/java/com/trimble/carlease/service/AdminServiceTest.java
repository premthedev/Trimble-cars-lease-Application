package com.trimble.carlease.service;

import com.trimble.carlease.model.Car;
import com.trimble.carlease.model.Lease;
import com.trimble.carlease.model.User;
import com.trimble.carlease.repository.CarRepository;
import com.trimble.carlease.repository.LeaseRepository;
import com.trimble.carlease.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = adminService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCars() {
        Car car1 = new Car();
        Car car2 = new Car();
        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        List<Car> cars = adminService.getAllCars();

        assertEquals(2, cars.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetAllLeases() {
        Lease lease1 = new Lease();
        Lease lease2 = new Lease();
        when(leaseRepository.findAll()).thenReturn(Arrays.asList(lease1, lease2));

        List<Lease> leases = adminService.getAllLeases();

        assertEquals(2, leases.size());
        verify(leaseRepository, times(1)).findAll();
    }

    @Test
    void testRemoveCar() {
        UUID carId = UUID.randomUUID();
        doNothing().when(carRepository).deleteById(carId);

        adminService.removeCar(carId);

        verify(carRepository, times(1)).deleteById(carId);
    }

    @Test
    void testRemoveUser() {
        UUID userId = UUID.randomUUID();
        doNothing().when(userRepository).deleteById(userId);

        adminService.removeUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}