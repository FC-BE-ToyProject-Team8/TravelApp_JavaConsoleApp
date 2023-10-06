package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.Builder;

@Builder
public record TripResponse(
        Long id,
        String name,
        LocalDate startAt,
        LocalDate endAt,
        List<ItineraryResponse> itineraries
) {

    public static TripResponse of(Trip trip) {
        return TripResponse.builder()
                .id(trip.getId())
                .name(trip.getName())
                .startAt(trip.getStartAt())
                .endAt(trip.getEndAt())
                .itineraries(getItineraryResponses(trip))
                .build();
    }

    private static List<ItineraryResponse> getItineraryResponses(Trip trip) {
        return trip.getItineraries().stream()
                .map(ItineraryResponse::of)
                .collect(Collectors.toList());
    }
}
