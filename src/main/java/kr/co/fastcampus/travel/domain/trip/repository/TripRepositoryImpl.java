package kr.co.fastcampus.travel.domain.trip.repository;

import java.util.List;
import java.util.Optional;

import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepository;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.file.TravelJsonFileManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepository {

    private static final String SEQUENCE_FIELD_NAME = "trip_id";

    private final ItineraryRepository itineraryRepository;
    private final TravelJsonFileManager travelJsonFileManager;

    @Override
    public List<Trip> findAll() {
        return travelJsonFileManager.findAllTrip();
    }

    @Override
    public Optional<Trip> findById(Long id) {
        Optional<Trip> findTrip = findTripById(id);
        findTrip.ifPresent(trip -> itineraryRepository.findByTrip(trip)
                .forEach(trip::addItinerary)
        );
        return findTrip;
    }

    private Optional<Trip> findTripById(Long id) {
        return travelJsonFileManager.findByTripId(id);
    }

    @Override
    public Trip save(Trip trip) {
        trip.setId(travelJsonFileManager.getSequence(SEQUENCE_FIELD_NAME));
        travelJsonFileManager.saveTripFile(trip);
        return trip;
    }
}
