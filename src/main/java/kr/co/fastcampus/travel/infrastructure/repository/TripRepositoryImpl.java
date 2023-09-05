package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;

public class TripRepositoryImpl implements TripRepository {

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
        return null;
    }
}
