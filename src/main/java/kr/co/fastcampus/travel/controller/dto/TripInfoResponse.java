package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;

public class TripInfoResponse {
    private Long id;
    private String name;
    private LocalDate startAt;
    private LocalDate endAt;

    public TripInfoResponse(Long id, String name, LocalDate startAt, LocalDate endAt) {
        this.id = id;
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Override
    public String toString() {
        return null;
    }
}
