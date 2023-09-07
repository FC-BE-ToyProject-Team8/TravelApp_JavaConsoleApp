package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepository {

    private static final String SEQUENCE_FIELD_NAME = "trip_id";

    private final TravelJsonRepository travelJsonRepository;
    private final TravelCsvRepository travelCsvRepository;

    @Override
    public List<Trip> findAll(FileType fileType) {
        return travelJsonRepository.findAll();
    }

    @Override
    public Optional<Trip> findById(FileType fileType, Long id) {
        return travelJsonRepository.findById(id);
    }

    @Override
    public Trip save(Trip trip) {
        trip.setId(travelJsonRepository.getSequence(SEQUENCE_FIELD_NAME));
        travelJsonRepository.saveTripFile(trip);
        travelCsvRepository.saveTripFile(trip);
        return trip;
    }
}
