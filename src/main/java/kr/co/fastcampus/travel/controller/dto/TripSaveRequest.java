package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.Builder;

@Builder
public record TripSaveRequest(
    String name,
    LocalDate startAt,
    LocalDate endAt,
    List<ItinerarySaveRequest> itinerarySaveRequests
) {

    public Trip toDomain() {
        return Trip.builder()
            .name(name)
            .startAt(startAt)
            .endAt(endAt)
            .build();
    }
}
