package kr.co.fastcampus.travel.infrastructure.repository;

import kr.co.fastcampus.travel.domain.Trip;

import java.util.List;
import java.util.Optional;

public class TripJsonRepository implements TripRepository {
    @Override
    public List<Trip> findAll() {
        return null;
    }

    @Override
    public Optional<Trip> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Trip save(Trip trip) {
        return null;
    }
}
