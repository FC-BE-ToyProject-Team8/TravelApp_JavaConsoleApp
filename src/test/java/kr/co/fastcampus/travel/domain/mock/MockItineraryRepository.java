package kr.co.fastcampus.travel.domain.mock;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepositoryImpl;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockItineraryRepository extends ItineraryRepositoryImpl {

    private List<Itinerary> itineraries;

    public MockItineraryRepository() {
        super(null);
        itineraries = new ArrayList<>();
    }

    @Override
    public List<Itinerary> findByTrip(Trip trip) {
        return itineraries.stream().filter(itinerary -> itinerary.getTrip().equals(trip)).toList();
    }

    @Override
    public Optional<Itinerary> findById(Long id) {
        return itineraries.stream().filter(itinerary -> itinerary.getId().equals(id)).findFirst();
    }

    @Override
    public Itinerary save(Itinerary itinerary) {
        itineraries.add(itinerary);
        return itinerary;
    }
}
