package kr.co.fastcampus.travel.domain.trip.service;

import kr.co.fastcampus.travel.domain.itinerary.dto.request.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepository;
import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepositoryImpl;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryLowService;
import kr.co.fastcampus.travel.domain.trip.dto.request.TripSaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepository;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TripServiceTests {

    private TripService tripService;
    private TripRepository mockTripRepository;
    private ItineraryRepository mockItineraryRepository;

    @BeforeEach
    void setUp() {
        this.mockTripRepository = new MockTripRepository();
        this.mockItineraryRepository = new MockItineraryRepository();

        this.tripService = new TripService(
            new TripLowService(mockTripRepository),
            new ItineraryLowService(mockItineraryRepository)
        );
    }

    @Test
    void findAllTrips() {
        // given
        Long id = 1L;
        Trip trip = Trip.builder().id(id).build();
        mockTripRepository.save(trip);

        // when
        List<Trip> trips = tripService.findAllTrips();

        // then
        assertThat(trips.size()).isEqualTo(1);
        assertThat(trips.get(0).getId()).isEqualTo(id);
    }

    @Test
    void saveTrip() {
        // given
        String name = "name";
        LocalDateTime departureAt = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime arriveAt = LocalDateTime.of(2010, 1, 1, 1, 1);
        List<ItinerarySaveRequest> itinerarySaveRequests = List.of(
            ItinerarySaveRequest.builder().departureAt(departureAt).arriveAt(arriveAt).build()
        );
        TripSaveRequest tripSaveRequest = TripSaveRequest.builder()
                .name(name).itinerarySaveRequests(itinerarySaveRequests).build();

        // when
        Trip trip = tripService.saveTrip(tripSaveRequest);

        // then
        List<Trip> trips = mockTripRepository.findAll();
        assertThat(trips.size()).isEqualTo(1);

        Trip savedTrip = trips.get(0);
        assertThat(savedTrip.getName()).isEqualTo(name);

        List<Itinerary> savedItineraries = savedTrip.getItineraries();
        assertThat(savedItineraries.size()).isEqualTo(1);

        Itinerary savedItinerary = savedItineraries.get(0);
        assertThat(savedItinerary.getRoute().getDepartureAt()).isEqualTo(departureAt);
        assertThat(savedItinerary.getRoute().getArriveAt()).isEqualTo(arriveAt);
    }

    @Test
    void getTrip() {
        // given
        Long id = 1L;
        String name = "name";
        Trip trip = Trip.builder().id(id).name(name).build();
        Trip savedTrip = mockTripRepository.save(trip);

        // when
        Trip gotTrip = tripService.getTrip(1L);

        // then
        assertThat(gotTrip).isEqualTo(savedTrip);
    }
}
class MockTripRepository extends TripRepositoryImpl {

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

class MockItineraryRepository extends ItineraryRepositoryImpl {

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

