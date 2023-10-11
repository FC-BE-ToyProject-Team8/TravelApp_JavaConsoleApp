package kr.co.fastcampus.travel.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.Trip;

public interface TripRepository {

    List<Trip> findAll();

    Optional<Trip> findById(Long id);

    Trip save(Trip trip);
}
