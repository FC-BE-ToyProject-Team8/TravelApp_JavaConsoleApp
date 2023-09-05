package kr.co.fastcampus.travel.infrastructure.repository;

import kr.co.fastcampus.travel.domain.Itinerary;

import java.util.List;

public interface ItineraryRepository {
    List<Itinerary> findByTripId(Long tripId);
    Itinerary findById(Long itineraryId);
    Itinerary save(Long tripId, Itinerary itinerary);
}
