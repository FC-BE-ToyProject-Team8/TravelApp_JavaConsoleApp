package kr.co.fastcampus.travel.domain.trip.dto.util;

import kr.co.fastcampus.travel.domain.trip.dto.request.TripSaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;

public class DtoToEntityConverter {
    public static Trip toEntity(TripSaveRequest saveRequest) {
        return Trip.builder()
                .name(saveRequest.name())
                .startAt(saveRequest.startAt())
                .endAt(saveRequest.endAt())
                .build();
    }
}
