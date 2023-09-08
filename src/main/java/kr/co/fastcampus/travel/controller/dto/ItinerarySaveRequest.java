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

    public ItinerarySaveRequest(String departure, String destination, LocalDateTime departureAt,
        LocalDateTime arriveAt, String accommodation, LocalDateTime checkInAt,
        LocalDateTime checkOutAt) {
        this.departure = departure;
        this.destination = destination;
        this.departureAt = departureAt;
        this.arriveAt = arriveAt;
        this.accommodation = accommodation;
        this.checkInAt = checkInAt;
        this.checkOutAt = checkOutAt;
    }
}
