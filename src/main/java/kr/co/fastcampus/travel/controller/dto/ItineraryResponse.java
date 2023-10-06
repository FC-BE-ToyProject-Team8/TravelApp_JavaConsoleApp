package kr.co.fastcampus.travel.controller.dto;


import java.time.LocalDateTime;
import kr.co.fastcampus.travel.domain.Itinerary;
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

    public static ItineraryResponse of(Itinerary itinerary) {
        return ItineraryResponse.builder()
                .id(itinerary.getId())
                .departure(itinerary.getRoute().getDeparture())
                .destination(itinerary.getRoute().getDestination())
                .departureAt(itinerary.getRoute().getDepartureAt())
                .arriveAt(itinerary.getRoute().getArriveAt())
                .accommodation(itinerary.getLodge().getAccommodation())
                .checkInAt(itinerary.getLodge().getCheckInAt())
                .checkOutAt(itinerary.getLodge().getCheckOutAt())
                .build();
    }
}
