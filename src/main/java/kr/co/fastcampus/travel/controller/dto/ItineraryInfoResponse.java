package kr.co.fastcampus.travel.controller.dto;

import kr.co.fastcampus.travel.domain.Itinerary;
import lombok.Builder;

@Builder
public record ItineraryInfoResponse(
        Long id,
        String departure,
        String destination
) {

    public static ItineraryInfoResponse of(Itinerary itinerary) {
        return ItineraryInfoResponse.builder()
                .id(itinerary.getId())
                .destination(itinerary.getRoute().getDestination())
                .departure(itinerary.getRoute().getDeparture())
                .build();
    }
}
