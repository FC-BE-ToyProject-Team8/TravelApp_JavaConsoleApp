package kr.co.fastcampus.travel.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Trip {

    private Long id;
    private String name;
    private LocalDate startAt;
    private LocalDate endAt;

    private final List<Itinerary> itineraries = new ArrayList<>();

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
}
