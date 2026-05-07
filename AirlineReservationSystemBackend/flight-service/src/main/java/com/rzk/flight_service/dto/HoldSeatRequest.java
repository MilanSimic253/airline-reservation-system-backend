package com.rzk.flight_service.dto;

import lombok.Data;

@Data
public class HoldSeatRequest {
    private Long flightId;
    private int seatNumber;
}
