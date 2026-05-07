package com.rzk.flight_service.dto;

import lombok.Data;

@Data
public class FlightSearchRequest {
    private String origin;
    private String destination;
}

