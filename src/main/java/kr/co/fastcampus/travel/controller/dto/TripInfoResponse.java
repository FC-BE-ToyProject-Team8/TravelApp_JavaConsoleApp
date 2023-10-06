package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.Builder;

@Builder
public record TripInfoResponse(
        Long id,
        String name,
        LocalDate startAt,
        LocalDate endAt
) {

    public static TripInfoResponse of(Trip trip) {
        return TripInfoResponse.builder()
                .id(trip.getId())
                .name(trip.getName())
                .startAt(trip.getStartAt())
                .endAt(trip.getEndAt())
                .build();
    }
}
