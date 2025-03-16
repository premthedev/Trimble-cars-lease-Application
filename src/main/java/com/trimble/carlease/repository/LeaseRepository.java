package com.trimble.carlease.repository;

import com.trimble.carlease.model.Lease;
import com.trimble.carlease.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeaseRepository extends JpaRepository<Lease, UUID> {
    List<Lease> findByCustomer(User customer);
    List<Lease> findByCarOwner(User owner);
}
