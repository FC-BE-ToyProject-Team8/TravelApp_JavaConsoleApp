package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDateTime;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
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

    public Itinerary toDomain(Trip trip) {
        return Itinerary.builder()
                .trip(trip)
                .departure(departure)
                .destination(destination)
                .departureAt(departureAt)
                .arriveAt(arriveAt)
                .accommodation(accommodation)
                .checkInAt(checkInAt)
                .checkOutAt(checkOutAt)
                .build();
    }
}
