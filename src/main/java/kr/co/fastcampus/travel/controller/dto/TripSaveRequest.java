package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;

public class TripSaveRequest {
    private LocalDate startAt;
    private LocalDate endAt;

    public TripSaveRequest(LocalDate startAt, LocalDate endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }
}
