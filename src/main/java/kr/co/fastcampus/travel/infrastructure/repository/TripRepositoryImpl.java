package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;

public class TripRepositoryImpl implements TripRepository {

    private static long SEQUENCE_NUMBER;

    private final TravelJsonRepository travelJsonRepository;

    public TripRepositoryImpl(TravelJsonRepository travelJsonRepository) {
        this.travelJsonRepository = travelJsonRepository;
        SEQUENCE_NUMBER = initSequence();
    }

    public long initSequence() {
        return travelJsonRepository.getTripCount();
    }

    @Override
    public List<Trip> findAll(FileType fileType) {
        return null;
    }

    @Override
    public Optional<Trip> findById(FileType fileType, Long id) {
        return Optional.empty();
    }

    @Override
    public Trip save(Trip trip) {
        trip.setId(SEQUENCE_NUMBER);
        travelJsonRepository.saveTripFile(trip);

        SEQUENCE_NUMBER++;
        return trip;
    }
}
