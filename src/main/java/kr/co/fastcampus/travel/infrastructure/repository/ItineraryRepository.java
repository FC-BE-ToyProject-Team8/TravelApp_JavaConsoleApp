package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.view.enums.FileType;

public interface ItineraryRepository {

    List<Itinerary> findByTrip(FileType fileType, Trip trip);

    Optional<Itinerary> findById(FileType fileType, Long id);

    Itinerary save(Itinerary itinerary);
}
