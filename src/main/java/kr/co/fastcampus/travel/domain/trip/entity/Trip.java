package kr.co.fastcampus.travel.domain.trip.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "itineraries")
@EqualsAndHashCode
public class Trip {

    private Long id;
    private String name;
    private LocalDate startAt;
    private LocalDate endAt;

    private List<Itinerary> itineraries = new ArrayList<>();

    @Builder
    private Trip(
            Long id,
            String name,
            LocalDate startAt,
            LocalDate endAt
    ) {
        this.id = id;
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addItinerary(Itinerary itinerary) {
        itineraries.add(itinerary);
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }
}
