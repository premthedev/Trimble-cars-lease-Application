package com.trimble.carlease.controller;

import com.trimble.carlease.dto.LeaseRequest;
import com.trimble.carlease.model.Lease;
import com.trimble.carlease.service.LeaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lease")
public class LeaseController {

    private static final Logger logger = LoggerFactory.getLogger(LeaseController.class);

    @Autowired
    private LeaseService leaseService;

    @PostMapping("/create")
    public Lease createLease(@RequestBody LeaseRequest leaseRequest){
        if (leaseRequest == null) {
            logger.error("Lease details are missing");
            throw new IllegalArgumentException("Lease details are missing");
        }
        logger.info("Creating lease with details: {}", leaseRequest);
        return leaseService.startLease(leaseRequest);
    }

    @PostMapping("/end/{leaseId}")
    public Lease endLease(@PathVariable UUID leaseId){
        if(leaseId == null){
            logger.error("Lease ID is missing");
            throw new IllegalArgumentException("Lease ID is missing");
        }
        logger.info("Ending lease with ID: {}", leaseId);
        return leaseService.endLease(leaseId);
    }

    @GetMapping("/history/{customerId}")
    public List<Lease> getLeaseHistory(@PathVariable UUID customerId){
        logger.info("Fetching lease history for customer with ID: {}", customerId);
        return leaseService.getLeaseHistory(customerId);
    }

    @GetMapping("/car-history/{ownerId}")
    public List<Lease> getCarHistory(@PathVariable UUID ownerId){
        logger.info("Fetching car history for owner with ID: {}", ownerId);
        return leaseService.getLeaseHistory(ownerId);
    }
}