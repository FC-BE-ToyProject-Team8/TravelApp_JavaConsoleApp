package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public record TripSaveRequest(
        String name,
        LocalDate startAt,
        LocalDate endAt,
        List<ItinerarySaveRequest> itinerarySaveRequests
) {

    public Trip toDomain() {
        Trip trip = Trip.builder()
                .name(name)
                .startAt(startAt)
                .endAt(endAt)
                .build();
        addItineraries(trip);
        return trip;
    }

    private void addItineraries(Trip trip) {
        itinerarySaveRequests.forEach(itinerarySaveRequest -> {
            Itinerary itinerary = itinerarySaveRequest.toDomain();
            itinerary.setTrip(trip);
        });
    }
}
