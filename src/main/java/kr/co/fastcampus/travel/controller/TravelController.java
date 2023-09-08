package kr.co.fastcampus.travel.controller;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.controller.dto.TripResponse;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.service.ItineraryConverter;
import kr.co.fastcampus.travel.service.ItineraryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelController {

	private final ItineraryService itineraryService;
	private final ItineraryConverter itineraryConverter;

	public List<TripInfoResponse> getTripList() {
		return null;
	}

	public TripResponse findTrip(Long id) {
		return null;
	}

	public String saveTrips(TripSaveRequest saveRequest) {
		return null;
	}

	public List<ItineraryInfoResponse> getItineraryList(FileType fileType, Long tripId) {
		List<Itinerary> response = itineraryService.findItineraries(fileType, tripId);
		List<ItineraryInfoResponse> itineraryInfoList = response.stream()
			.map(itineraryConverter::toInfoDto).collect(Collectors.toList());
		return itineraryInfoList;
	}

	public ItineraryResponse findItinerary(FileType fileType, Long id) {
		Itinerary response = itineraryService.findItinerary(fileType, id);
		if (response == null) {
			throw new TravelDoesNotExistException();
		}
		return itineraryConverter.toResponseDto(response);
	}

	public String saveItineraries(Long tripId, List<ItinerarySaveRequest> saveRequests) {
		return null;
	}
}
