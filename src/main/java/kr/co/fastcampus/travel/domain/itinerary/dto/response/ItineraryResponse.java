package kr.co.fastcampus.travel.domain.itinerary.dto.response;


import java.time.LocalDateTime;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
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
