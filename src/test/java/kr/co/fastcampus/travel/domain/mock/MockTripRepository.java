package kr.co.fastcampus.travel.domain.mock;

import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockTripRepository extends TripRepositoryImpl {

    private List<Trip> trips;

    public MockTripRepository() {
        super(null, null);
        this.trips = new ArrayList<>();
    }

    @Override
    public List<Trip> findAll() {
        return trips;
    }

    @Override
    public Optional<Trip> findById(Long id) {
        return trips.stream().filter(trip -> trip.getId() == id).findFirst();
    }

    @Override
    public Trip save(Trip trip) {
        trips.add(trip);
        return trip;
    }
}
