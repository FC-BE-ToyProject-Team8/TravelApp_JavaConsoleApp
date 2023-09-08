package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.travel.domain.Itinerary;

public record TripResponse(Long id, String name, LocalDate startAt, LocalDate endAt,
                           List<Itinerary> itineraries) {

    @Override
    public String toString() {
        return null;
    }
}
