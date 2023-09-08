package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.TripRepository;
import kr.co.fastcampus.travel.infrastructure.repository.TripRepositoryImpl;

public class TripService {

    private final ItineraryService itineraryService;
    private final TripRepository tripRepository;

    public TripService() {
        this.tripRepository = new TripRepositoryImpl();
        this.itineraryService = new ItineraryService();
    }

    public TripService(ItineraryService itineraryService, TripRepository tripRepository) {
        this.itineraryService = itineraryService;
        this.tripRepository = tripRepository;
    }

    public List<Trip> findAllTrips() {
        return null;
    }

    public List<Trip> findAllTrips(FileType fileType) {
        return tripRepository.findAll(fileType);
    }

    public Trip findTrip(FileType fileType, Long id) {
        Optional<Trip> trip = tripRepository.findById(fileType, id);
        return trip.orElse(null);
    }

    public Trip saveTrip(TripSaveRequest saveRequest) {
        Trip trip = Trip.builder()
            .name(saveRequest.name())
            .startAt(saveRequest.startAt())
            .endAt(saveRequest.endAt())
            .build();

        Trip savedTrip = tripRepository.save(trip);

        List<Itinerary> itineraries = itineraryService.saveItineraries(
            savedTrip.getId(),
            saveRequest.itinerarySaveRequests()
        );
        itineraries.forEach(trip::addItinerary);

        return trip;
    }
}
