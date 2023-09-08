package kr.co.fastcampus.travel.controller.dto;

import kr.co.fastcampus.travel.domain.Itinerary;

public record ItineraryInfoResponse(Long id, String departure, String destination) {

	public ItineraryInfoResponse(Itinerary itinerary) {
		this(itinerary.getId(),
			itinerary.getRoute().getDeparture(),
			itinerary.getRoute().getDestination());
	}
}
