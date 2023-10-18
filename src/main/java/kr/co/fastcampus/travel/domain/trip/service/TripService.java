package kr.co.fastcampus.travel.domain.trip.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.domain.trip.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripService {

    public final TripRepository tripRepository;

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
        return tripRepository.save(trip);
    }
}
