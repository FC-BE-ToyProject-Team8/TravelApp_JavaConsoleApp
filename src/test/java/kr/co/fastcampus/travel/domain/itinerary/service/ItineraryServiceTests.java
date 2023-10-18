package kr.co.fastcampus.travel.domain.itinerary.service;

import kr.co.fastcampus.travel.domain.itinerary.dto.request.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepository;
import kr.co.fastcampus.travel.domain.mock.MockItineraryRepository;
import kr.co.fastcampus.travel.domain.mock.MockTripRepository;
import kr.co.fastcampus.travel.domain.trip.dto.request.TripSaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.trip.repository.TripRepository;
import kr.co.fastcampus.travel.domain.trip.service.TripLowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ItineraryServiceTests {

    private ItineraryService itineraryService;
    private TripRepository mockTripRepository;
    private ItineraryRepository mockItineraryRepository;

    @BeforeEach
    void setUp() {
        this.mockTripRepository = new MockTripRepository();
        this.mockItineraryRepository = new MockItineraryRepository();

        this.itineraryService = new ItineraryService(
            new ItineraryLowService(this.mockItineraryRepository),
            new TripLowService(this.mockTripRepository)
        );
    }

    @Test
    void findItinerary() {
        // given
        Long tripId = 1L;
        Long itineraryId = 1L;
        LocalDateTime departureAt = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime arriveAt = LocalDateTime.of(2010, 1, 1, 1, 1);

        Trip savedTrip = mockTripRepository.save(Trip.builder().id(tripId).build());
        Itinerary savedItinerary = mockItineraryRepository.save(
                Itinerary.builder().departureAt(departureAt).arriveAt(arriveAt).id(itineraryId).trip(savedTrip).build()
        );

        // when
        Itinerary findedItinerary = itineraryService.findItinerary(itineraryId);

        // then
        assertThat(findedItinerary).isEqualTo(savedItinerary);
    }

    @Test
    void findItineraries() {
        // given
        Long tripId = 1L;
        Long itineraryId = 1L;
        LocalDateTime departureAt = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime arriveAt = LocalDateTime.of(2010, 1, 1, 1, 1);

        Trip savedTrip = mockTripRepository.save(Trip.builder().id(tripId).build());
        Itinerary savedItinerary = mockItineraryRepository.save(
                Itinerary.builder().departureAt(departureAt).arriveAt(arriveAt).id(itineraryId).trip(savedTrip).build()
        );

        // when
        List<Itinerary> findedItineraries = itineraryService.findItineraries(tripId);

        // then
        assertThat(findedItineraries.size()).isEqualTo(1);

        Itinerary findedItinerary = findedItineraries.get(0);
        assertThat(findedItinerary).isEqualTo(savedItinerary);

    }

    @Test
    void saveItineraries() {
        // given
        Long tripId = 1L;
        LocalDateTime departureAt = LocalDateTime.of(2010, 1, 1, 0, 0);
        LocalDateTime arriveAt = LocalDateTime.of(2010, 1, 1, 1, 1);

        Trip trip = Trip.builder().id(tripId).build();
        Trip savedTrip = mockTripRepository.save(trip);

        List<ItinerarySaveRequest> itinerarySaveRequests = List.of(
                ItinerarySaveRequest.builder().departureAt(departureAt).arriveAt(arriveAt).build()
        );

        // when
        List<Itinerary> savedItineraries = itineraryService.saveItineraries(tripId, itinerarySaveRequests);

        // then
        assertThat(savedItineraries.size()).isEqualTo(1);

        Itinerary itinerary = savedItineraries.get(0);
        assertThat(itinerary.getRoute().getDepartureAt()).isEqualTo(departureAt);
        assertThat(itinerary.getRoute().getArriveAt()).isEqualTo(arriveAt);
        assertThat(itinerary.getTrip()).isEqualTo(savedTrip);
    }
}
