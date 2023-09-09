package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.TripRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripService {

    public final TripRepository tripRepository;

    public List<Trip> findAllTrips(FileType fileType) {
        return tripRepository.findAll(fileType);
    }

    public Trip findTrip(FileType fileType, Long id) {
        Optional<Trip> trip = tripRepository.findById(fileType, id);
        return trip.orElse(null);
    }

    public Trip saveTrip(TripSaveRequest saveRequest) {
        Trip trip = saveRequest.toDomain();
        return tripRepository.save(trip);
    }
}
