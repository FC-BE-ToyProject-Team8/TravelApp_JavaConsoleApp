package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.Optional;
import kr.co.fastcampus.travel.domain.Itinerary;

import java.util.List;

public interface ItineraryRepository {
    List<Itinerary> findByTripId(Long tripId);
    Optional<Itinerary> findById(Long itineraryId);
    Optional<Itinerary> save(Long tripId, Itinerary itinerary);
}
