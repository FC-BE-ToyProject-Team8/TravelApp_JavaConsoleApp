package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDateTime;
import kr.co.fastcampus.travel.common.exception.InvalidParamException;
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

    public ItinerarySaveRequest {
        if (departureAt != null && arriveAt != null && arriveAt.isBefore(departureAt)) {
            throw new InvalidParamException("도착 시간이 출발 시간보다 빠를 수 없습니다.");
        }

        if (checkInAt != null && checkOutAt != null && checkOutAt.isBefore(checkInAt)) {
            throw new InvalidParamException("체크아웃 시간이 체크인 시간보다 빠를 수 없습니다.");
        }
    }
}
