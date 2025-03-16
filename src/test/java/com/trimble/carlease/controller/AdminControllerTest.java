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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private CarService carService;

    @Mock
    private LeaseService leaseService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

//    @Test
//    void testGetAllUsers() throws Exception {
//        List<User> users = Arrays.asList(new User(), new User());
//        when(adminService.getAllUsers()).thenReturn(users);
//
//        mockMvc.perform(get("/api/admin/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//
//        verify(adminService, times(1)).getAllUsers();
//    }

    @Test
    void testRemoveUser() throws Exception {
        UUID userId = UUID.randomUUID();
        doNothing().when(adminService).removeUser(userId);

        mockMvc.perform(delete("/api/admin/remove-user/{userId}", userId))
                .andExpect(status().isOk());

        verify(adminService, times(1)).removeUser(userId);
    }

    @Test
    void testRegisterCar() throws Exception {
        when(carService.registerCar(any(CarRequest.class))).thenReturn(new Car());

        mockMvc.perform(post("/api/admin/cars/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ownerId\":\"2663bd7f-ffda-4bf4-8d7d-c1b4b21584dc\",\"make\":\"Toyota\",\"model\":\"Camry\",\"year\":2020}"))
                .andExpect(status().isOk());

        verify(carService, times(1)).registerCar(any(CarRequest.class));
    }

//    @Test
//    void testGetAllCars() throws Exception {
//        List<Car> cars = Arrays.asList(new Car(), new Car());
//        when(adminService.getAllCars()).thenReturn(cars);
//
//        mockMvc.perform(get("/api/admin/cars"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//
//        verify(adminService, times(1)).getAllCars();verify(adminService, atLeastOnce()).getAllCars();
//    }

    @Test
    void testGetCarStatus() throws Exception {
        UUID carId = UUID.randomUUID();
        when(carService.getCarStatus(carId)).thenReturn("IDLE");

        mockMvc.perform(get("/api/admin/cars/status/{carId}", carId))
                .andExpect(status().isOk())
                .andExpect(content().string("IDLE"));

        verify(carService, times(1)).getCarStatus(carId);
    }

    @Test
    void testRemoveCar() throws Exception {
        UUID carId = UUID.randomUUID();
        doNothing().when(adminService).removeCar(carId);

        mockMvc.perform(delete("/api/admin/remove-car/{carId}", carId))
                .andExpect(status().isOk());

        verify(adminService, times(1)).removeCar(carId);
    }

    @Test
    void testGetAllLeases() throws Exception {
        List<Lease> leases = Arrays.asList(new Lease(), new Lease());
        when(adminService.getAllLeases()).thenReturn(leases);

        mockMvc.perform(get("/api/admin/leases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(adminService, times(1)).getAllLeases();
    }

    @Test
    void testStartLease() throws Exception {
        when(leaseService.startLease(any(LeaseRequest.class))).thenReturn(new Lease());

        mockMvc.perform(post("/api/admin/leases/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"carId\":\"2663bd7f-ffda-4bf4-8d7d-c1b4b21584dc\",\"customerId\":\"2663bd7f-ffda-4bf4-8d7d-c1b4b21584dc\"}"))
                .andExpect(status().isOk());

        verify(leaseService, times(1)).startLease(any(LeaseRequest.class));
    }

    @Test
    void testGetOwnerLeaseHistory() throws Exception {
        UUID ownerId = UUID.randomUUID();
        List<Lease> leases = Arrays.asList(new Lease(), new Lease());
        when(leaseService.getOwnerLeaseHistory(ownerId)).thenReturn(leases);

        mockMvc.perform(get("/api/admin/leases/owner-history/{ownerId}", ownerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(leaseService, times(1)).getOwnerLeaseHistory(ownerId);
    }

    @Test
    void testGetCustomerLeaseHistory() throws Exception {
        UUID customerId = UUID.randomUUID();
        List<Lease> leases = Arrays.asList(new Lease(), new Lease());
        when(leaseService.getLeaseHistory(customerId)).thenReturn(leases);

        mockMvc.perform(get("/api/admin/leases/customer-history/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(leaseService, times(1)).getLeaseHistory(customerId);
    }
}