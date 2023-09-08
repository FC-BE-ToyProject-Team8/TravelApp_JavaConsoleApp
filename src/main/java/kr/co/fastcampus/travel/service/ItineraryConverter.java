package kr.co.fastcampus.travel.service;

import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.domain.Itinerary;

public class ItineraryConverter {

	public ItineraryInfoResponse toInfoDto(Itinerary itinerary) {
		return ItineraryInfoResponse.builder()
			.id(itinerary.getId())
			.departure(itinerary.getRoute().getDeparture())
			.destination(itinerary.getRoute().getDestination())
			.build();
	}

	public ItineraryResponse toResponseDto(Itinerary itinerary) {
		return ItineraryResponse.builder()
			.id(itinerary.getId())
			.departure(itinerary.getRoute().getDeparture())
			.destination(itinerary.getRoute().getDestination())
			.departureAt(itinerary.getRoute().getDepartureAt())
			.arriveAt(itinerary.getRoute().getArriveAt())
			.accommodation(itinerary.getLodge().getAccommodation())
			.checkInAt(itinerary.getLodge().getCheckInAt())
			.checkOutAt(itinerary.getLodge().getCheckOutAt())
			.build();
	}
}
