package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.ItineraryRepository;
import kr.co.fastcampus.travel.infrastructure.repository.ItineraryRepositoryImpl;

public class ItineraryService {

    private final TripService tripService;
    private final ItineraryRepository itineraryRepository;

    public ItineraryService() {
        this.tripService = new TripService();
        this.itineraryRepository = new ItineraryRepositoryImpl();
    }

    public ItineraryService(TripService tripService, ItineraryRepository itineraryRepository) {
        this.tripService = tripService;
        this.itineraryRepository = itineraryRepository;
    }

    public Itinerary findItinerary(FileType fileType, Long id) {
        Optional<Itinerary> response = itineraryRepository.findById(fileType, id);
        return response.orElseThrow(TravelDoesNotExistException::new);
    }

    public List<Itinerary> findItineraries(FileType fileType, Long tripId) {
        Trip trip = tripService.findTrip(tripId);
        return itineraryRepository.findByTripId(fileType, trip);
    }

    public List<Itinerary> saveItineraries(Long tripId) {
        return null;
    }
}
