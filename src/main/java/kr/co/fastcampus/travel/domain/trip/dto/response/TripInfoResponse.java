package kr.co.fastcampus.travel.domain.trip.dto.response;

import java.time.LocalDate;

import kr.co.fastcampus.travel.domain.trip.entity.Trip;

public record TripInfoResponse(
    Long id,
    String name,
    LocalDate startAt,
    LocalDate endAt
) {

    public TripInfoResponse(Trip trip) {
        this(
            trip.getId(),
            trip.getName(),
            trip.getStartAt(),
            trip.getEndAt()
        );
    }
}
