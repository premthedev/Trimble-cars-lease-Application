package com.trimble.carlease.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LeaseRequest {
private UUID  carId;
private UUID  customerId;
}