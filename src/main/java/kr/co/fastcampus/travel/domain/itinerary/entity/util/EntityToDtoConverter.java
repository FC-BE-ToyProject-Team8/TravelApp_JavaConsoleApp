package kr.co.fastcampus.travel.domain.itinerary.entity.util;

import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryInfoResponse;
import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryResponse;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;

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

    public static ItineraryInfoResponse toItineraryInfoResponse(Itinerary itinerary) {
        return new ItineraryInfoResponse(
                itinerary.getId(),
                itinerary.getRoute().getDeparture(),
                itinerary.getRoute().getDestination()
        );
    }
}
