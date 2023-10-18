package kr.co.fastcampus.travel.domain.trip.controller.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;

public record TripResponse(
    Long id,
    String name,
    LocalDate startAt,
    LocalDate endAt,
    List<ItineraryResponse> itineraries
) {

    public TripResponse(Trip trip) {
        this(
            trip.getId(),
            trip.getName(),
            trip.getStartAt(),
            trip.getEndAt(),
            trip.getItineraries().stream().map(itinerary -> new ItineraryResponse(itinerary)).toList()
        );
    }
}
