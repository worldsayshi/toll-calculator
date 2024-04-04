package com.acme.tollCalculator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleEventRepository extends JpaRepository<VehicleEvent, Long> {
}
