package kr.co.fastcampus.travel.domain.itinerary.entity;

import java.time.LocalDateTime;

import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "trip")
public class Itinerary {

    private Long id;
    private Route route;
    private Lodge lodge;
    private Trip trip;

    @Builder
    private Itinerary(
            Long id,
            String departure,
            String destination,
            LocalDateTime departureAt,
            LocalDateTime arriveAt,
            String accommodation,
            LocalDateTime checkInAt,
            LocalDateTime checkOutAt,
            Trip trip
    ) {
        this.id = id;
        this.route = new Route(departure, destination, departureAt, arriveAt);
        this.lodge = new Lodge(accommodation, checkInAt, checkOutAt);
        this.trip = trip;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
