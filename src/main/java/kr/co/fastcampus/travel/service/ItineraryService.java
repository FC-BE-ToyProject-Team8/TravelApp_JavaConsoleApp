package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItineraryService {
	private final TripService tripService;
	private final ItineraryRepository itineraryRepository;

	public Itinerary findItinerary(FileType fileType, Long id) {
		Optional<Itinerary> response = itineraryRepository.findById(fileType, id);
		if (response.isPresent()) {
			return response.get();
		} else {
			return null;
		}
	}

	public List<Itinerary> findItineraries(FileType fileType, Long tripId) {
		Trip trip = tripService.findTrip(tripId);
		return itineraryRepository.findByTripId(fileType, trip);
	}

	public List<Itinerary> saveItineraries(Long tripId) {
		return null;
	}
}
