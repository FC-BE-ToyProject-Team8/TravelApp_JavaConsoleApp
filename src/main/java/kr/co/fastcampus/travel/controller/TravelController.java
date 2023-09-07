package kr.co.fastcampus.travel.controller;

import java.util.List;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.controller.dto.TripResponse;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.service.ItineraryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelController {

	private final ItineraryService itineraryService;

	public List<TripInfoResponse> getTripList() {
		return null;
	}

	public TripResponse findTrip(Long id) {
		return null;
	}

	public String saveTrips(TripSaveRequest saveRequest) {
		return null;
	}

	public List<ItineraryResponse> getItineraryList(Long tripId) {
		return null;
	}

	public ItineraryResponse findItinerary(FileType fileType, Long id) {
		// TOOD
		// fileType, id의 valid check

		Itinerary response = itineraryService.findItinerary(fileType, id);
		if (response == null) {
			throw new TravelDoesNotExistException("해당 id의 여정이 존재하지 않습니다.");
		}

		return ItineraryResponse.builder()
			.id(response.getId())
			.departure(response.getRoute().getDeparture())
			.destination(response.getRoute().getDestination())
			.departureAt(response.getRoute().getDepartureAt())
			.arriveAt(response.getRoute().getArriveAt())
			.accommodation(response.getLodge().getAccommodation())
			.checkInAt(response.getLodge().getCheckInAt())
			.checkOutAt(response.getLodge().getCheckOutAt())
			.build();
	}

	public String saveItineraries(Long tripId, List<ItinerarySaveRequest> saveRequests) {
		return null;
	}
}
