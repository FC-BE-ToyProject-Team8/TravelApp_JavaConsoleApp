package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItineraryRepositoryImpl implements ItineraryRepository {

    private static long SEQUENCE_NUMBER = initSequence();

    private final TravelJsonRepository travelJsonRepository;

    public static long initSequence() {
        // todo: Application 시작 시, 마지막 Sequence로 초기화 로직 필요
        return 1L;
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

        SEQUENCE_NUMBER++;
        return itinerary;
    }
}
