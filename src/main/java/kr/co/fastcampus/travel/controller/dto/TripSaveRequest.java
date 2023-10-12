package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.travel.common.exception.InvalidParamException;

public record TripSaveRequest(
        String name,
        LocalDate startAt,
        LocalDate endAt,
        List<ItinerarySaveRequest> itinerarySaveRequests
) {

    public TripSaveRequest {
        if (name == null) {
            throw new InvalidParamException();
        }

        if (startAt != null && endAt != null && endAt.isBefore(startAt)) {
            throw new InvalidParamException("종료 날짜가 시작 날짜보다 빠를 수 없습니다.");
        }
    }
}
