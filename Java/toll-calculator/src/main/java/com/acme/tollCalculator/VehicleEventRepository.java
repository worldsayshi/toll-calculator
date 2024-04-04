package com.acme.tollCalculator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehicleEventRepository extends JpaRepository<VehicleEvent, Long> {
    List<VehicleEvent> findByRegistrationNumberAndEventDateBetween(String registrationNumber, LocalDateTime startDate, LocalDateTime endDate);
}
