package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;

public interface TripRepository {
    List<Trip> findAll(FileType fileType);
    Optional<Trip> findById(FileType fileType, Long id);
    Trip save(Trip trip);
}
