package kr.co.fastcampus.travel.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public interface ItineraryRepository {

    List<Itinerary> findByTrip(Trip trip);

    Optional<Itinerary> findById(Long id);

    Itinerary save(Itinerary itinerary);
}
