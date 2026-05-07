package com.rzk.booking_service.service;

import com.rzk.booking_service.feign.FlightProxy;
import com.rzk.booking_service.dto.HoldSeatRequest;
import com.rzk.booking_service.exception.BookingException;
import com.rzk.booking_service.model.Booking;
import com.rzk.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightProxy flightProxy;

    public Booking createBooking(Long userId, Long flightId, int seatNumber) {
        try {
            HoldSeatRequest request = new HoldSeatRequest();
            request.setFlightId(flightId);
            request.setSeatNumber(seatNumber);
            flightProxy.holdSeat(request);

            Booking booking = Booking.builder()
                    .userId(userId)
                    .flightId(flightId)
                    .seatNumber(seatNumber)
                    .confirmed(true)
                    .build();

            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new BookingException("Greška pri kreiranju rezervacije: " + e.getMessage());
        }
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingException("Rezervacija nije pronađena"));

        HoldSeatRequest request = new HoldSeatRequest();
        request.setFlightId(booking.getFlightId());
        request.setSeatNumber(booking.getSeatNumber());

        flightProxy.cancelSeat(request);

        bookingRepository.delete(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


}
