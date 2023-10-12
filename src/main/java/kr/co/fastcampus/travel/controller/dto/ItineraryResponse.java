package kr.co.fastcampus.travel.controller.dto;


import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ItineraryResponse(
        Long id,
        String departure,
        String destination,
        LocalDateTime departureAt,
        LocalDateTime arriveAt,
        String accommodation,
        LocalDateTime checkInAt,
        LocalDateTime checkOutAt
) {

}
