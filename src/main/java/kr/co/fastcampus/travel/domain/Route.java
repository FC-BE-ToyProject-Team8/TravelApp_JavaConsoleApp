package kr.co.fastcampus.travel.domain;

import java.time.LocalDateTime;

public class Route {
    private String departure;
    private String destination;
    private LocalDateTime departureAt;
    private LocalDateTime arriveAt;

    public Route(String departure, String destination, LocalDateTime departureAt, LocalDateTime arriveAt) {
        this.departure = departure;
        this.destination = destination;
        this.departureAt = departureAt;
        this.arriveAt = arriveAt;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureAt() {
        return departureAt;
    }

    public LocalDateTime getArriveAt() {
        return arriveAt;
    }
}
