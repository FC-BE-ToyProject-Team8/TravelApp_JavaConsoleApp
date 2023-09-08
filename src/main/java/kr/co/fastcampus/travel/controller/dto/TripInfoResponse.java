package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;

public record TripInfoResponse(Long id, String name, LocalDate startAt, LocalDate endAt) {

    @Override
    public String toString() {
        return String.format("%d: %s (%s ~ %s)", id, name, startAt, endAt);
    }
}
