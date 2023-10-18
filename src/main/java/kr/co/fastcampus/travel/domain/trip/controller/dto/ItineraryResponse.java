package kr.co.fastcampus.travel.domain.trip.controller.dto;


import java.time.LocalDateTime;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import lombok.Builder;

@Builder
public record ItineraryResponse(
    Long id,
    String departure,
    String destination,
    LocalDateTime departureAt,
    LocalDateTime arriveAt,
    String accommodation,
    LocalDateTime checkInAt,
    LocalDateTime checkOutAt
) {

    public ItineraryResponse(Itinerary itinerary) {
        this(
            itinerary.getId(),
            itinerary.getRoute().getDeparture(),
            itinerary.getRoute().getDestination(),
            itinerary.getRoute().getDepartureAt(),
            itinerary.getRoute().getArriveAt(),
            itinerary.getLodge().getAccommodation(),
            itinerary.getLodge().getCheckInAt(),
            itinerary.getLodge().getCheckOutAt()
        );
    }
}
