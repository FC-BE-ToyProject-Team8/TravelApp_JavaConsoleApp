package kr.co.fastcampus.travel.infrastructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public class ItineraryRepositoryImpl implements ItineraryRepository {

    private static long SEQUENCE_NUMBER;

    private final TravelJsonRepository travelJsonRepository;
    private final TravelCsvRepository travelCsvRepository;

    public ItineraryRepositoryImpl(
            TravelJsonRepository travelJsonRepository,
            TravelCsvRepository travelCsvRepository
    ) {
        this.travelJsonRepository = travelJsonRepository;
        this.travelCsvRepository = travelCsvRepository;
        SEQUENCE_NUMBER = initSequence();
    }

    public long initSequence() {
        return travelJsonRepository.getItineraryCount();
    }

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
        itinerary.setId(SEQUENCE_NUMBER);
        travelJsonRepository.saveTripInfoFile(itinerary);
        travelJsonRepository.saveItineraryFile(itinerary);
        travelCsvRepository.saveTripInfoFile(itinerary);
        travelCsvRepository.saveItineraryFile(itinerary);

        SEQUENCE_NUMBER++;
        return itinerary;
    }
}
