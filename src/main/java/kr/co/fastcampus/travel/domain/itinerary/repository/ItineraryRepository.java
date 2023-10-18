package kr.co.fastcampus.travel.domain.itinerary.repository;

import java.util.List;
import java.util.Optional;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;

public interface ItineraryRepository {

    List<Itinerary> findByTrip(Trip trip);

    Optional<Itinerary> findById(Long id);

    Itinerary save(Itinerary itinerary);
}
