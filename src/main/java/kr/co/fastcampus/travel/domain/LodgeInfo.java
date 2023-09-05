package kr.co.fastcampus.travel.domain;

import java.time.LocalDateTime;

public class LodgeInfo {
    private String accommodation;
    private LocalDateTime checkInAt;
    private LocalDateTime checkOutAt;

    public LodgeInfo(String accommodation, LocalDateTime checkInAt, LocalDateTime checkOutAt) {
        this.accommodation = accommodation;
        this.checkInAt = checkInAt;
        this.checkOutAt = checkOutAt;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public LocalDateTime getCheckInAt() {
        return checkInAt;
    }

    public LocalDateTime getCheckOutAt() {
        return checkOutAt;
    }
}
