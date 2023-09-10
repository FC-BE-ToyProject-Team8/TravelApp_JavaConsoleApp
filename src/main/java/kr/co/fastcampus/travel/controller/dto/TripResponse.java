package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public record TripResponse(
    Long id,
    String name,
    LocalDate startAt,
    LocalDate endAt,
    List<ItineraryResponse> itineraries
) {

    public TripResponse(Trip trip, List<Itinerary> itineraries) {
        this(
            trip.getId(),
            trip.getName(),
            trip.getStartAt(),
            trip.getEndAt(),
            convertItinerariesToItineraryResponses(itineraries)
        );
    }

    private static List<ItineraryResponse> convertItinerariesToItineraryResponses(
        List<Itinerary> itineraries
    ) {
        List<ItineraryResponse> itineraryResponses = new ArrayList<>();
        for (Itinerary itinerary : itineraries) {
            itineraryResponses.add(new ItineraryResponse(itinerary));
        }
        return itineraryResponses;
    }
}
