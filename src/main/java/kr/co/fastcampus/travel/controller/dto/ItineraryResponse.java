package kr.co.fastcampus.travel.controller.dto;


import java.time.LocalDateTime;

public class ItineraryResponse {
    private Long id;
    private String departure;
    private String destination;
    private LocalDateTime departureAt;
    private LocalDateTime arriveAt;
    private String accommodation;
    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return null;
    }
}
