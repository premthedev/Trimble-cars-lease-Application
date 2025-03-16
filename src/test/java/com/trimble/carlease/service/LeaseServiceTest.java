package com.trimble.carlease.service;

import com.trimble.carlease.dto.LeaseRequest;
import com.trimble.carlease.model.Car;
import com.trimble.carlease.model.CarStatus;
import com.trimble.carlease.model.Lease;
import com.trimble.carlease.model.User;
import com.trimble.carlease.repository.CarRepository;
import com.trimble.carlease.repository.LeaseRepository;
import com.trimble.carlease.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaseServiceTest {

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LeaseService leaseService;

    private LeaseRequest leaseRequest;
    private User customer;
    private Car car;
    private Lease lease;

    @BeforeEach
    void setUp() {
        leaseRequest = new LeaseRequest();
        leaseRequest.setCarId(UUID.randomUUID());
        leaseRequest.setCustomerId(UUID.randomUUID());

        customer = new User();
        customer.setId(leaseRequest.getCustomerId());

        car = new Car();
        car.setId(leaseRequest.getCarId());
        car.setStatus(CarStatus.IDLE);

        lease = new Lease();
        lease.setId(UUID.randomUUID());
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStartDate(LocalDateTime.now());
    }

    @Test
    void testStartLease() {
        when(userRepository.findById(leaseRequest.getCustomerId())).thenReturn(Optional.of(customer));
        when(carRepository.findById(leaseRequest.getCarId())).thenReturn(Optional.of(car));
        when(leaseRepository.save(any(Lease.class))).thenReturn(lease);

        Lease startedLease = leaseService.startLease(leaseRequest);

        assertEquals(lease.getId(), startedLease.getId());
        assertEquals(CarStatus.ON_LEASE, car.getStatus());
        verify(userRepository, times(1)).findById(leaseRequest.getCustomerId());
        verify(carRepository, times(1)).findById(leaseRequest.getCarId());
        verify(carRepository, times(1)).save(car);
        verify(leaseRepository, times(1)).save(any(Lease.class));
    }

    @Test
    void testStartLease_UserNotFound() {
        when(userRepository.findById(leaseRequest.getCustomerId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaseService.startLease(leaseRequest));
        verify(userRepository, times(1)).findById(leaseRequest.getCustomerId());
        verify(carRepository, never()).findById(any(UUID.class));
        verify(leaseRepository, never()).save(any(Lease.class));
    }

    @Test
    void testStartLease_CarNotFound() {
        when(userRepository.findById(leaseRequest.getCustomerId())).thenReturn(Optional.of(customer));
        when(carRepository.findById(leaseRequest.getCarId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaseService.startLease(leaseRequest));
        verify(userRepository, times(1)).findById(leaseRequest.getCustomerId());
        verify(carRepository, times(1)).findById(leaseRequest.getCarId());
        verify(leaseRepository, never()).save(any(Lease.class));
    }

    @Test
    void testEndLease() {
        when(leaseRepository.findById(lease.getId())).thenReturn(Optional.of(lease));
        when(leaseRepository.save(any(Lease.class))).thenReturn(lease);

        Lease endedLease = leaseService.endLease(lease.getId());

        assertNotNull(endedLease.getEndDate());
        assertEquals(CarStatus.IDLE, car.getStatus());
        verify(leaseRepository, times(1)).findById(lease.getId());
        verify(carRepository, times(1)).save(car);
        verify(leaseRepository, times(1)).save(lease);
    }

    @Test
    void testEndLease_LeaseNotFound() {
        when(leaseRepository.findById(lease.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaseService.endLease(lease.getId()));
        verify(leaseRepository, times(1)).findById(lease.getId());
        verify(carRepository, never()).save(any(Car.class));
        verify(leaseRepository, never()).save(any(Lease.class));
    }

    @Test
    void testGetLeaseHistory() {
        when(userRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(leaseRepository.findByCustomer(customer)).thenReturn(Arrays.asList(lease));

        List<Lease> leaseHistory = leaseService.getLeaseHistory(customer.getId());

        assertEquals(1, leaseHistory.size());
        assertEquals(lease.getId(), leaseHistory.get(0).getId());
        verify(userRepository, times(1)).findById(customer.getId());
        verify(leaseRepository, times(1)).findByCustomer(customer);
    }

    @Test
    void testGetLeaseHistory_CustomerNotFound() {
        when(userRepository.findById(customer.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaseService.getLeaseHistory(customer.getId()));
        verify(userRepository, times(1)).findById(customer.getId());
        verify(leaseRepository, never()).findByCustomer(any(User.class));
    }
}