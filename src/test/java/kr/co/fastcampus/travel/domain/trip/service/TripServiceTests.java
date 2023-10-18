package kr.co.fastcampus.travel.domain.trip.service;

import kr.co.fastcampus.travel.domain.itinerary.dto.request.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepository;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryLowService;
import kr.co.fastcampus.travel.domain.mock.MockItineraryRepository;
import kr.co.fastcampus.travel.domain.mock.MockTripRepository;
import kr.co.fastcampus.travel.domain.trip.dto.request.TripSaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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

