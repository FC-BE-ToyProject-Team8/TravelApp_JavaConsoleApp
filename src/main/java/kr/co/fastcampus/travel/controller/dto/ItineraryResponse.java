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

    @Override
    public String toString() {
        return null;
    }
}
