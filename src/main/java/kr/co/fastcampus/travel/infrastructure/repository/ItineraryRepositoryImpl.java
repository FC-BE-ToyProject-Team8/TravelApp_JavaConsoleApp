package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public class ItineraryRepositoryImpl implements ItineraryRepository {

    @Override
    public List<Itinerary> findByTripId(FileType fileType, Trip trip) {
        return null;
    }

    @Override
    public Optional<Itinerary> findById(FileType fileType, Long itineraryId) {
        return Optional.empty();
    }

    @Override
    public Itinerary save(Itinerary itinerary) {
        return null;
    }
}
