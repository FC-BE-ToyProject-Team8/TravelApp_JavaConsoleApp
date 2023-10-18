package kr.co.fastcampus.travel.domain.trip.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;

public interface TripRepository {

    List<Trip> findAll();

    Optional<Trip> findById(Long id);

    Trip save(Trip trip);
}
