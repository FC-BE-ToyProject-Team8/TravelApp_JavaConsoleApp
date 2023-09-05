package kr.co.fastcampus.travel.infrastructure.repository;

import kr.co.fastcampus.travel.domain.Trip;

import java.util.List;
import java.util.Optional;

// itineraries는 null로 채운다
public interface TripRepository {
    List<Trip> findAll();
    Optional<Trip> findById(Long id);
    Optional<Trip> save(Trip trip);
}
