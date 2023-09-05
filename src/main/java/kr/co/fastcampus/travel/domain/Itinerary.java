package kr.co.fastcampus.travel.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Itinerary {
    private Long id;
    private Route route;
    private Lodge lodge;

    @Builder
    public Itinerary(
        Long id,
        String departure,
        String destination,
        LocalDateTime departureAt,
        LocalDateTime arriveAt,
        String accommodation,
        LocalDateTime checkInAt,
        LocalDateTime checkOutAt)
    {
        this.id = id;
        this.route = new Route(departure, destination, departureAt, arriveAt);
        this.lodge = new Lodge(accommodation, checkInAt, checkOutAt);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
