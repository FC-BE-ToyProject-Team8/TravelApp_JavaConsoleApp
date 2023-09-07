package kr.co.fastcampus.travel.infrastructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepository {

    public static void main(String[] args) {
        TripRepositoryImpl target = new TripRepositoryImpl(
                new TravelJsonRepository(new ObjectMapper().registerModule(new JavaTimeModule())),
                new TravelCsvRepository());
        target.findAll(FileType.CSV).forEach(System.out::println);
    }

    private static final String SEQUENCE_FIELD_NAME = "trip_id";

    private final TravelJsonRepository travelJsonRepository;
    private final TravelCsvRepository travelCsvRepository;

    @Override
    public List<Trip> findAll(FileType fileType) {
        if (fileType == FileType.CSV) {
            return travelCsvRepository.findAllTrip();
        }
        return travelJsonRepository.findAllTrip();
    }

    @Override
    public Optional<Trip> findById(FileType fileType, Long id) {
        return travelJsonRepository.findByTripId(id);
    }

    @Override
    public Trip save(Trip trip) {
        trip.setId(travelJsonRepository.getSequence(SEQUENCE_FIELD_NAME));
        travelJsonRepository.saveTripFile(trip);
        travelCsvRepository.saveTripFile(trip);
        return trip;
    }
}
