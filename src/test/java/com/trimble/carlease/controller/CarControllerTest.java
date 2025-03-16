package com.trimble.carlease.controller;

import com.trimble.carlease.dto.CarRequest;
import com.trimble.carlease.model.Car;
import com.trimble.carlease.service.CarService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testRegisterCar() throws Exception {
        when(carService.registerCar(any(CarRequest.class))).thenReturn(new Car());

        mockMvc.perform(post("/api/car/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ownerId\":\"2663bd7f-ffda-4bf4-8d7d-c1b4b21584dc\",\"make\":\"Toyota\",\"model\":\"Camry\",\"year\":2020}"))
                .andExpect(status().isOk());

        verify(carService, times(1)).registerCar(any(CarRequest.class));
    }

//    @Test
//    void testGetAvailableCars() throws Exception {
//        List<Car> cars = Arrays.asList(new Car(), new Car());
//        when(carService.getAvailableCars()).thenReturn(cars);
//
//        mockMvc.perform(get("/api/car/getCars"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//
//        verify(carService, times(1)).getAvailableCars();
//    }
}