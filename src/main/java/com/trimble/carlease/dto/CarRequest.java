package com.trimble.carlease.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class CarRequest {
    private UUID ownerId;
    private String make;
    private String model;
    private int year;
}
