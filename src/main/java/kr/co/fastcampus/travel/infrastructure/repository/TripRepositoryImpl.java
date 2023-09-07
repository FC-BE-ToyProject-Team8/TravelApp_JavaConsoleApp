package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.file.TravelCsvFileManager;
import kr.co.fastcampus.travel.infrastructure.repository.file.TravelJsonFileManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepository {

    private static final String SEQUENCE_FIELD_NAME = "trip_id";

    private final TravelJsonFileManager travelJsonFileManager;
    private final TravelCsvFileManager travelCsvFileManager;

    @Override
    public List<Trip> findAll(FileType fileType) {
        if (fileType == FileType.CSV) {
            return travelCsvFileManager.findAllTrip();
        }
        return travelJsonFileManager.findAllTrip();
    }

    @Override
    public Optional<Trip> findById(FileType fileType, Long id) {
        if (fileType == FileType.CSV) {
            return travelCsvFileManager.findByTripId(id);
        }
        return travelJsonFileManager.findByTripId(id);
    }

    @Override
    public Trip save(Trip trip) {
        trip.setId(travelJsonFileManager.getSequence(SEQUENCE_FIELD_NAME));
        travelJsonFileManager.saveTripFile(trip);
        travelCsvFileManager.saveTripFile(trip);
        return trip;
    }
}
