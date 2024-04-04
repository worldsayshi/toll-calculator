package com.acme.tollCalculator;

//import javax.persistence.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class VehicleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    // Constructors, getters, and setters
    public VehicleEvent() {
    }

    public VehicleEvent(String registrationNumber, LocalDateTime eventDate) {
        this.registrationNumber = registrationNumber;
        this.eventDate = eventDate;
    }

    // Standard getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
