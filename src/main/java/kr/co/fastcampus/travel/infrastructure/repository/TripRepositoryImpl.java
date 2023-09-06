package kr.co.fastcampus.travel.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepository {

    private static long SEQUENCE_NUMBER = initSequence();

    private final TravelJsonRepository travelJsonRepository;

    @Override
    public List<Trip> findAll(FileType fileType) {
        return null;
    }

    @Override
    public Optional<Trip> findById(FileType fileType, Long id) {
        return Optional.empty();
    }

    @Override
    public Trip save(Trip trip) {
        trip.setId(SEQUENCE_NUMBER);
        travelJsonRepository.saveTripFile(trip);

        SEQUENCE_NUMBER++;
        return trip;
    }

    public static long initSequence() {
        // todo: Application 시작 시, 마지막 Sequence로 초기화 로직 필요
        return 1L;
    }
}
