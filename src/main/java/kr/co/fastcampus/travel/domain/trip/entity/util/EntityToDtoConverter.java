package kr.co.fastcampus.travel.domain.trip.entity.util;

import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryResponse;
import kr.co.fastcampus.travel.domain.trip.dto.response.TripInfoResponse;
import kr.co.fastcampus.travel.domain.trip.dto.response.TripResponse;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;

import java.util.List;

import static kr.co.fastcampus.travel.domain.itinerary.entity.util.EntityToDtoConverter.toItineraryResponse;

public class EntityToDtoConverter {

    public static List<TripInfoResponse> toTripInfoResponses(List<Trip> trips) {
        return trips.stream()
                .map(EntityToDtoConverter::toTripInfoResponse)
                .toList();
    }

    public static TripInfoResponse toTripInfoResponse(Trip trip) {
        return new TripInfoResponse(
            trip.getId(),
            trip.getName(),
            trip.getStartAt(),
            trip.getEndAt()
        );
    }

    public static TripResponse toTripResponse(Trip trip) {
        return new TripResponse(
            trip.getId(),
            trip.getName(),
            trip.getStartAt(),
            trip.getEndAt(),
            trip.getItineraries().stream().map(itinerary -> toItineraryResponse(itinerary)).toList()
        );
    }
}
