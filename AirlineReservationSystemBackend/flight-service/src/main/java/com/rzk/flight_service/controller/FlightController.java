package com.rzk.flight_service.controller;

import com.rzk.flight_service.dto.FlightSearchRequest;
import com.rzk.flight_service.dto.HoldSeatRequest;
import com.rzk.flight_service.model.Flight;
import com.rzk.flight_service.model.Flightseat;
import com.rzk.flight_service.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/allFlightsUser")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/seats/{flightId}")
    public List<Flightseat> getAvailableSeats(@PathVariable Long flightId) {
        return flightService.getAvailableSeats(flightId);
    }

    @GetMapping("/admin/allFlights")
    public List<Flight> getAllFlightsAdmin() {
        return flightService.getAllFlights(); // ista funkcija, ali admin može da vidi i dodatne informacije
    }

    @PostMapping("/admin/addFlight")
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }

    @PutMapping("/admin/updateFlight/{flightId}")
    public Flight updateFlight(@PathVariable Long flightId, @RequestBody Flight flight) {
        return flightService.updateFlight(flightId, flight);
    }

    @DeleteMapping("/admin/deleteFlight/{id}")
    public String deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return "Let je uspešno obrisan!";
    }

    @GetMapping("/admin/seats/{flightId}")
    public List<Flightseat> getAllSeats(@PathVariable Long flightId) {
        return flightService.getAllSeats(flightId);
    }

    @PostMapping("/admin/addSeat")
    public Flightseat addSeat(@RequestBody Flightseat seat) {
        return flightService.addSeat(seat);
    }

    @PutMapping("/admin/updateSeat/{seatId}")
    public Flightseat updateSeat(@PathVariable Long seatId, @RequestBody Flightseat seat) {
        return flightService.updateSeat(seatId, seat);
    }

    @DeleteMapping("/admin/deleteSeat/{seatId}")
    public void deleteSeat(@PathVariable Long seatId) {
        flightService.deleteSeat(seatId);
    }

    @PostMapping("/holdSeat")
    public Flightseat holdSeat(@RequestBody HoldSeatRequest request) {
        return flightService.holdSeat(request);
    }

    @PostMapping("/cancelSeat")
    public Flightseat cancelSeat(@RequestBody HoldSeatRequest request) {
        return flightService.cancelSeat(request);
    }

}
