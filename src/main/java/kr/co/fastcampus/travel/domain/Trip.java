package kr.co.fastcampus.travel.domain;

import java.time.LocalDate;
import java.util.List;

public class Trip {
    private Long id;
    private LocalDate startAt;
    private LocalDate endAt;
    private List<Itinerary> itineraries;

    public Trip(Long id, LocalDate startAt, LocalDate endAt, List<Itinerary> itineraries) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.itineraries = itineraries;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }
}
