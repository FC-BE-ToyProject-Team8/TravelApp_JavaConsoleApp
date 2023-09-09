package kr.co.fastcampus.travel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.TripRepositoryImpl;
import kr.co.fastcampus.travel.infrastructure.repository.file.TravelCsvFileManager;
import kr.co.fastcampus.travel.infrastructure.repository.file.TravelJsonFileManager;

public class TripService {

    public final TripRepositoryImpl tripRepository;

    public TripService() {
        this.tripRepository = new TripRepositoryImpl(
            new TravelJsonFileManager(new ObjectMapper()),
            new TravelCsvFileManager()
        );
    }

    public TripService(TripRepositoryImpl tripRepository) {
        this.tripRepository = tripRepository;
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

        return tripRepository.save(trip);
    }
}
