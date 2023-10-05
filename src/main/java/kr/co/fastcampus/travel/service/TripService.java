package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.repository.TripRepository;
import kr.co.fastcampus.travel.view.enums.FileType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripService {

    public final TripRepository tripRepository;

    public List<Trip> findAllTrips(FileType fileType) {
        List<Trip> trips = tripRepository.findAll(fileType);
        if (trips.isEmpty()) {
            throw new TravelDoesNotExistException();
        }
        return trips;
    }

    public Trip findTrip(FileType fileType, Long id) {
        Optional<Trip> trip = tripRepository.findById(fileType, id);
        return trip.orElseThrow(TravelDoesNotExistException::new);
    }

    public Trip saveTrip(TripSaveRequest saveRequest) {
        Trip trip = saveRequest.toDomain();
        return tripRepository.save(trip);
    }
}
