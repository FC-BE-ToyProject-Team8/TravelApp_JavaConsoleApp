package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDateTime;

public class ItinerarySaveRequest {
    private String departure;
    private String destination;
    private LocalDateTime departureAt;
    private LocalDateTime arriveAt;
    private String accommodation;
    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;

    public ItinerarySaveRequest(String departure, String destination, LocalDateTime departureAt,
        LocalDateTime arriveAt, String accommodation, LocalDateTime checkInAt,
        LocalDateTime checkOutAt) {
        this.departure = departure;
        this.destination = destination;
        this.departureAt = departureAt;
        this.arriveAt = arriveAt;
        this.accommodation = accommodation;
        this.checkInAt = checkInAt;
        this.checkOutAt = checkOutAt;
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

    public String getAccommodation() {
        return accommodation;
    }

    public LocalDateTime getCheckInAt() {
        return checkInAt;
    }

    public LocalDateTime getCheckOutAt() {
        return checkOutAt;
    }
}
