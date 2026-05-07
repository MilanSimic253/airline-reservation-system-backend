package com.rzk.flight_service.service;

import com.rzk.flight_service.dto.FlightSearchRequest;
import com.rzk.flight_service.dto.HoldSeatRequest;
import com.rzk.flight_service.exception.FlightException;
import com.rzk.flight_service.model.Flight;
import com.rzk.flight_service.model.Flightseat;
import com.rzk.flight_service.repository.FlightRepository;
import com.rzk.flight_service.repository.FlightSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightSeatRepository flightSeatRepository;

    public Flightseat holdSeat(HoldSeatRequest request) {
        List<Flightseat> seats = flightSeatRepository.findByFlightId(request.getFlightId());
        return seats.stream()
                .filter(s -> s.getSeatNumber() == request.getSeatNumber() && !s.isReserved())
                .findFirst()
                .map(seat -> {
                    seat.setReserved(true);
                    return flightSeatRepository.save(seat);
                })
                .orElseThrow(() -> new FlightException("Sedište je već rezervisano ili ne postoji"));
    }

    public Flightseat cancelSeat(HoldSeatRequest request) {
        List<Flightseat> seats = flightSeatRepository.findByFlightId(request.getFlightId());
        return seats.stream()
                .filter(s -> s.getSeatNumber() == request.getSeatNumber() && s.isReserved())
                .findFirst()
                .map(seat -> {
                    seat.setReserved(false);
                    return flightSeatRepository.save(seat);
                })
                .orElseThrow(() -> new FlightException("Sedište nije rezervisano ili ne postoji"));
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public List<Flightseat> getAvailableSeats(Long flightId) {
        return flightSeatRepository.findByFlightId(flightId)
                .stream()
                .filter(seat -> !seat.isReserved())
                .toList();
    }

    public List<Flightseat> getAllSeats(Long flightId) {
        return flightSeatRepository.findByFlightId(flightId);
    }

    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public Flight updateFlight(Long flightId, Flight updatedFlight) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightException("Let nije pronađen"));
        flight.setOrigin(updatedFlight.getOrigin());
        flight.setDestination(updatedFlight.getDestination());
        flight.setDepartureTime(updatedFlight.getDepartureTime());
        flight.setPrice(updatedFlight.getPrice());
        return flightRepository.save(flight);
    }

    public Flightseat addSeat(Flightseat seat) {
        flightRepository.findById(seat.getFlightId())
                .orElseThrow(() -> new FlightException("Let sa ID " + seat.getFlightId() + " ne postoji"));

        return flightSeatRepository.save(seat);
    }

    public Flightseat updateSeat(Long seatId, Flightseat updatedSeat) {
        Flightseat seat = flightSeatRepository.findById(seatId)
                .orElseThrow(() -> new FlightException("Sedište nije pronađeno"));
        seat.setSeatNumber(updatedSeat.getSeatNumber());
        seat.setReserved(updatedSeat.isReserved());
        seat.setFlightId(updatedSeat.getFlightId());
        return flightSeatRepository.save(seat);
    }

    public void deleteSeat(Long seatId) {
        Flightseat seat = flightSeatRepository.findById(seatId)
                .orElseThrow(() -> new FlightException("Sedište nije pronađeno"));
        flightSeatRepository.delete(seat);
    }
}
