package kr.co.fastcampus.travel.controller.dto;

import kr.co.fastcampus.travel.domain.Itinerary;

import java.time.LocalDate;
import java.util.List;

public class TripResponse {
    private Long id;
    private String name;
    private LocalDate startAt;
    private LocalDate endAt;
    private List<Itinerary> itineraries;

    public TripResponse(Long id, LocalDate startAt, LocalDate endAt, List<Itinerary> itineraries) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.itineraries = itineraries;
    }

    @Override
    public String toString() {
        return null;
    }
}
