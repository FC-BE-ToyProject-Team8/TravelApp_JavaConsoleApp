package kr.co.fastcampus.travel.domain.itinerary.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Route {

    private String departure;
    private String destination;
    private LocalDateTime departureAt;
    private LocalDateTime arriveAt;

    public Route(
            String departure,
            String destination,
            LocalDateTime departureAt,
            LocalDateTime arriveAt
    ) {
        this.departure = departure;
        this.destination = destination;
        this.departureAt = departureAt;
        this.arriveAt = arriveAt;
    }
}
