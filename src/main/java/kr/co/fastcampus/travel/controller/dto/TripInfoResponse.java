package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import java.util.SimpleTimeZone;
import kr.co.fastcampus.travel.domain.Trip;

public record TripInfoResponse(Long id,
                               String name,
                               LocalDate startAt,
                               LocalDate endAt,
                               int itinerariesCount
) {
    public TripInfoResponse(Trip trip) {
        this(trip.getId(), trip.getName(), trip.getStartAt(), trip.getEndAt(), trip.getItineraries().size());
    }
}
