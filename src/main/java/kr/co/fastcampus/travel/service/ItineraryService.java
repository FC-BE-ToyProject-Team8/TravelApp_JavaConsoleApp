package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.infrastructure.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItineraryService {

	private final ItineraryRepository itineraryRepository;

	public Itinerary findItinerary(FileType fileType, Long id) {
		Optional<Itinerary> response = itineraryRepository.findById(fileType, id);
		if (response.isPresent()) {
			Itinerary itineraryEntity = response.get();
			return Itinerary.builder()
				.id(itineraryEntity.getId())
				.departure(itineraryEntity.getRoute().getDeparture())
				.destination(itineraryEntity.getRoute().getDestination())
				.departureAt(itineraryEntity.getRoute().getDepartureAt())
				.arriveAt(itineraryEntity.getRoute().getArriveAt())
				.accommodation(itineraryEntity.getLodge().getAccommodation())
				.checkInAt(itineraryEntity.getLodge().getCheckInAt())
				.checkOutAt(itineraryEntity.getLodge().getCheckOutAt())
				.build();
		} else {
			return null;
		}
	}

	public List<Itinerary> findItineraries(Long tripId) {
		return null;
	}

	public List<Itinerary> saveItineraries(Long tripId,
		List<ItinerarySaveRequest> itinerarySaveRequests) {
		return null;
	}
}
