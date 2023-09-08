package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record TripSaveRequest(
    String name,
    LocalDate startAt,
    LocalDate endAt,
    List<ItinerarySaveRequest> itinerarySaveRequests
) {

}
