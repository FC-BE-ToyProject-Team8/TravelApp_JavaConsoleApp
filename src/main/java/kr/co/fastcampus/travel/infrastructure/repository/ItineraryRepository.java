package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;

public interface ItineraryRepository {
    List<Itinerary> findByTripId(FileType fileType, Long tripId);
    Optional<Itinerary> findById(FileType fileType, Long itineraryId);
    Itinerary save(Long tripId, Itinerary itinerary);
}
