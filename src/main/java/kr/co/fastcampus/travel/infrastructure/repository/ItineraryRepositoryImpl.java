package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;

public class ItineraryRepositoryImpl implements ItineraryRepository {

    @Override
    public List<Itinerary> findByTripId(FileType fileType, Long tripId) {
        return null;
    }

    @Override
    public Optional<Itinerary> findById(FileType fileType, Long itineraryId) {
        return Optional.empty();
    }

    @Override
    public Itinerary save(Long tripId, Itinerary itinerary) {
        return null;
    }
}
