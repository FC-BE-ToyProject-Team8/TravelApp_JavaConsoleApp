package kr.co.fastcampus.travel.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.repository.ItineraryRepository;
import kr.co.fastcampus.travel.view.enums.FileType;

public class FakeItineraryRepository implements ItineraryRepository {

    private Long sequence = 0L;
    private final Map<Long, Itinerary> store = new HashMap<>();

    @Override
    public List<Itinerary> findByTrip(FileType fileType, Trip trip) {
        return store.keySet().stream()
                .filter(key -> store.get(key).getTrip().getId().equals(trip.getId()))
                .map(store::get)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Itinerary> findById(FileType fileType, Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Itinerary save(Itinerary itinerary) {
        itinerary.setId(sequence);
        store.put(sequence++, itinerary);
        return itinerary;
    }
}
