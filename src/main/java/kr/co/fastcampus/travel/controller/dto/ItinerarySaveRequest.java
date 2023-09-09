package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ItinerarySaveRequest(
        String departure,
        String destination,
        LocalDateTime departureAt,
        LocalDateTime arriveAt,
        String accommodation,
        LocalDateTime checkInAt,
        LocalDateTime checkOutAt
) {

    public ItinerarySaveRequest {
        if ((departureAt == null && arriveAt == null)
            || (checkInAt == null && checkOutAt == null)) {
            throw new IllegalArgumentException();
        }
    }
}
