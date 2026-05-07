package com.rzk.booking_service.controller;

import com.rzk.booking_service.model.Booking;
import com.rzk.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestParam Long userId, @RequestParam Long flightId, @RequestParam int seatNumber) {
        return bookingService.createBooking(userId, flightId, seatNumber);
    }

    @GetMapping("/userBookings/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }

    @DeleteMapping("/cancelBooking/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "Rezervacija je uspešno otkazana";
    }

    @GetMapping("/admin/allBookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

}

