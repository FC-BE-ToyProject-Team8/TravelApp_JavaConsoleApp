package kr.co.fastcampus.travel.domain.trip.service;

import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TripLowService {

    public final TripRepository tripRepository;


    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip findTrip(Long id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.orElseThrow(TravelDoesNotExistException::new);
    }
}
