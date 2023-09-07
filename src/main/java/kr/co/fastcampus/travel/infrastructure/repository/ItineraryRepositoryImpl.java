package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItineraryRepositoryImpl implements ItineraryRepository {

    private static final String SEQUENCE_FIELD_NAME = "itinerary_id";

    private final TravelJsonRepository travelJsonRepository;
    private final TravelCsvRepository travelCsvRepository;

    @Override
    public List<Itinerary> findByTripId(FileType fileType, Trip trip) {
        return travelJsonRepository.findByTrip(trip);
    }

    @Override
    public Optional<Itinerary> findById(FileType fileType, Long itineraryId) {
        return Optional.empty();
    }

    @Override
    public Itinerary save(Itinerary itinerary) {
        itinerary.setId(travelJsonRepository.getSequence(SEQUENCE_FIELD_NAME));
        travelJsonRepository.saveItineraryFile(itinerary);
        travelCsvRepository.saveItineraryFile(itinerary);
        return itinerary;
    }
}
