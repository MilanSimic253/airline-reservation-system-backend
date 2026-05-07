package com.rzk.flight_service.repository;

import com.rzk.flight_service.model.Flightseat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightSeatRepository extends JpaRepository<Flightseat, Long> {
    List<Flightseat> findByFlightId(Long flightId);
}
