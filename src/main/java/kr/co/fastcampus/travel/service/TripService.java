package kr.co.fastcampus.travel.service;

import java.util.Optional;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;

import java.util.List;
import kr.co.fastcampus.travel.infrastructure.repository.TripRepository;

public class TripService {

    private TripRepository tripRepository;

    public List<Trip> findAllTrips(FileType fileType) {
        List<Trip> trips = tripRepository.findAll(fileType);
        return trips;
    }

    public Trip findTrip(FileType fileType, Long id) {
        Optional<Trip> trip = tripRepository.findById(fileType, id);
        return trip.orElse(null);
    }

    public Trip saveTrip(TripSaveRequest saveRequest) {
        return null;
    }
}
