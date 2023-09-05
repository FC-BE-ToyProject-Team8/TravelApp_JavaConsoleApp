package kr.co.fastcampus.travel.infrastructure.repository;

import kr.co.fastcampus.travel.domain.Itinerary;

import java.util.List;

public class ItineraryCsvRepository implements ItineraryRepository {
    @Override
    public List<Itinerary> findByTripId(Long tripId) {
        return null;
    }

    @Override
    public Itinerary save(Long tripId, Itinerary itinerary) {
        return null;
    }

    @Override
    public Itinerary findById(Long itineraryId) {
        return null;
    }
}
