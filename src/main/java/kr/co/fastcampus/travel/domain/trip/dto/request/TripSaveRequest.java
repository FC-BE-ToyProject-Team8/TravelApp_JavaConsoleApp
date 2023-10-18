package kr.co.fastcampus.travel.domain.trip.dto.request;

import java.time.LocalDate;
import java.util.List;

import kr.co.fastcampus.travel.domain.itinerary.dto.request.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import lombok.Builder;

@Builder
public record TripSaveRequest(
    String name,
    LocalDate startAt,
    LocalDate endAt,
    List<ItinerarySaveRequest> itinerarySaveRequests
) {

}
