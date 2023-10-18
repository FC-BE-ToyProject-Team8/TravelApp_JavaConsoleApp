package kr.co.fastcampus.travel.domain.itinerary.entity.util;

import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryInfoResponse;
import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryResponse;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class EntityToDtoConverter {

    public static ItineraryResponse toItineraryResponse(Itinerary itinerary) {
        return new ItineraryResponse(
                itinerary.getId(),
                itinerary.getRoute().getDeparture(),
                itinerary.getRoute().getDestination(),
                itinerary.getRoute().getDepartureAt(),
                itinerary.getRoute().getArriveAt(),
                itinerary.getLodge().getAccommodation(),
                itinerary.getLodge().getCheckInAt(),
                itinerary.getLodge().getCheckOutAt()
        );
    }

    public static List<ItineraryInfoResponse> toItineraryInfoResponses(List<Itinerary> itinerary) {
        return itinerary.stream()
                .map(EntityToDtoConverter::toItineraryInfoResponse)
                .toList();
    }

    public static ItineraryInfoResponse toItineraryInfoResponse(Itinerary itinerary) {
        return new ItineraryInfoResponse(
                itinerary.getId(),
                itinerary.getRoute().getDeparture(),
                itinerary.getRoute().getDestination()
        );
    }
}
