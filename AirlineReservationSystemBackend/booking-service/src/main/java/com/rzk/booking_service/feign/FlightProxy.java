package com.rzk.booking_service.feign;

import com.rzk.booking_service.dto.HoldSeatRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "flight-service")
public interface FlightProxy {
    @PostMapping("/flights/holdSeat")
    void holdSeat(@RequestBody HoldSeatRequest request);

    @PostMapping("/flights/cancelSeat")
    void cancelSeat(@RequestBody HoldSeatRequest request);
}


