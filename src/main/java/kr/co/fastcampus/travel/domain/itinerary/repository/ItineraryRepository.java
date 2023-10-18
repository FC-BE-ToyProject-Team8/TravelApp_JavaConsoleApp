package kr.co.fastcampus.travel.domain.itinerary.repository;

import java.util.List;
import java.util.Optional;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.view.enums.FileType;

public interface ItineraryRepository {

    List<Itinerary> findByTrip(FileType fileType, Trip trip);

    Optional<Itinerary> findById(FileType fileType, Long id);

    Itinerary save(Itinerary itinerary);
}
