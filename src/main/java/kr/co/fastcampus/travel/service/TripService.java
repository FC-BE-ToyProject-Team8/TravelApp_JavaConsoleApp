package kr.co.fastcampus.travel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.TripRepositoryImpl;
import kr.co.fastcampus.travel.infrastructure.repository.file.TravelCsvFileManager;
import kr.co.fastcampus.travel.infrastructure.repository.file.TravelJsonFileManager;

public class TripService {

    public final TripRepositoryImpl tripRepository;
    private final ItineraryService itineraryService;

    public TripService() {
        this.tripRepository = new TripRepositoryImpl(
            new TravelJsonFileManager(new ObjectMapper()),
            new TravelCsvFileManager()
        );
        this.itineraryService = new ItineraryService();
    }

    public TripService(TripRepositoryImpl tripRepository, ItineraryService itineraryService) {
        this.tripRepository = tripRepository;
        this.itineraryService = itineraryService;
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
