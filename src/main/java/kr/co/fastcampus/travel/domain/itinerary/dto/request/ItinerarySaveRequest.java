package kr.co.fastcampus.travel.domain.itinerary.dto.request;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ItinerarySaveRequest(
    String departure,
    String destination,
    LocalDateTime departureAt,
    LocalDateTime arriveAt,
    String accommodation,
    LocalDateTime checkInAt,
    LocalDateTime checkOutAt
) {

    // TODO: @Valid로 추후 변경
    public ItinerarySaveRequest {
        if ((departureAt == null && arriveAt == null)
            && (checkInAt == null && checkOutAt == null)) {
            throw new IllegalArgumentException();
        }
    }
}