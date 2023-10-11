package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.repository.TripRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripService {

    private final ItineraryService itineraryService;
    private final TripRepository tripRepository;

    public List<Trip> findAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        if (trips.isEmpty()) {
            throw new TravelDoesNotExistException();
        }
        return trips;
    }

    public Trip findTrip(Long id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.orElseThrow(TravelDoesNotExistException::new);
    }

    public Trip saveTrip(TripSaveRequest saveRequest) {
        Trip trip = saveRequest.toDomain();
        trip = tripRepository.save(trip);
        itineraryService.saveItineraries(trip, saveRequest.itinerarySaveRequests());
        return trip;
    }
}
