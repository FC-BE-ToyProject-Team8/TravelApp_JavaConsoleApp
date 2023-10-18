package kr.co.fastcampus.travel.domain.trip.dto.response;

import java.time.LocalDate;
import java.util.List;

import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryResponse;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;

public record TripResponse(
    Long id,
    String name,
    LocalDate startAt,
    LocalDate endAt,
    List<ItineraryResponse> itineraries
) {

}
