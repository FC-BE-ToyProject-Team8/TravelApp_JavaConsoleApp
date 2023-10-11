package kr.co.fastcampus.travel.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.repository.file.TravelCsvFileManager;
import kr.co.fastcampus.travel.repository.file.TravelJsonFileManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItineraryRepositoryImpl implements ItineraryRepository {

    private static final String SEQUENCE_FIELD_NAME = "itinerary_id";

    private final TravelJsonFileManager travelJsonFileManager;
    private final TravelCsvFileManager travelCsvFileManager;

    @Override
    public List<Itinerary> findByTrip(Trip trip) {
        return travelJsonFileManager.findByTrip(trip);
    }

    @Override
    public Optional<Itinerary> findById(Long id) {
        return travelJsonFileManager.findByItineraryId(id);
    }

    @Override
    public Itinerary save(Itinerary itinerary) {
        itinerary.setId(travelJsonFileManager.getSequence(SEQUENCE_FIELD_NAME));
        travelJsonFileManager.saveItineraryFile(itinerary);
        travelCsvFileManager.saveItineraryFile(itinerary);
        return itinerary;
    }
}
