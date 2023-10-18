package kr.co.fastcampus.travel.domain.itinerary.dto.util;

import kr.co.fastcampus.travel.domain.itinerary.dto.request.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;

public class DtoToEntityConverter {

    public static Itinerary toEntity(Trip trip, ItinerarySaveRequest dto) {
        return Itinerary.builder()
                .trip(trip)
                .departure(dto.departure())
                .destination(dto.destination())
                .departureAt(dto.departureAt())
                .arriveAt(dto.arriveAt())
                .accommodation(dto.departure())
                .checkInAt(dto.checkInAt())
                .checkOutAt(dto.checkOutAt())
                .build();
    }
}
