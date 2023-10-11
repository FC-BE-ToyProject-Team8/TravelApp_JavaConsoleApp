package kr.co.fastcampus.travel.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.repository.TripRepository;

public class FakeTripRepository implements TripRepository {

    private Long sequence = 0L;
    private final Map<Long, Trip> store = new HashMap<>();

    @Override
    public List<Trip> findAll() {
        return store.keySet().stream()
                .map(store::get)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Trip> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Trip save(Trip trip) {
        trip.setId(sequence);
        store.put(sequence++, trip);
        return trip;
    }
}
