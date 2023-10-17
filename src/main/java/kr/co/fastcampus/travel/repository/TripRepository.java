package kr.co.fastcampus.travel.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.view.enums.FileType;

public interface TripRepository {

    List<Trip> findAll(FileType fileType);

    Optional<Trip> findById(FileType fileType, Long id);

    Trip save(Trip trip);
}
