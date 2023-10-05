package kr.co.fastcampus.travel.service;

import static kr.co.fastcampus.travel.view.enums.FileType.CSV;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.mock.FakeItineraryRepository;
import kr.co.fastcampus.travel.mock.FakeTripRepository;
import kr.co.fastcampus.travel.repository.ItineraryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("여정 서비스 테스트")
class ItineraryServiceTest {

    public static final String DEPARTURE = "출발지";
    public static final String DESTINATION = "목적지";
    public static final String ACCOMMODATION = "숙소";
    public static final LocalDateTime NOW = LocalDateTime.now();

    private Trip trip;

    private ItineraryService itineraryService;
    private ItineraryRepository itineraryRepository;

    @BeforeEach
    void setup() {
        FakeTripRepository tripRepository = new FakeTripRepository();
        TripService tripService = new TripService(tripRepository);
        itineraryRepository = new FakeItineraryRepository();
        itineraryService = new ItineraryService(tripService, itineraryRepository);

        trip = tripRepository.save(Trip.builder()
                .name("여행")
                .startAt(LocalDate.now())
                .endAt(LocalDate.now().plusDays(3))
                .build());
    }

    @Test
    @DisplayName("여정 저장")
    void saveItinerary() {
        // given
        List<ItinerarySaveRequest> request = IntStream.range(0, 3)
                .mapToObj(this::createItinerarySaveRequest)
                .toList();

        // when
        List<Itinerary> result = itineraryService.saveItineraries(trip.getId(), request);

        // then
        assertEquals(3, result.size());
        IntStream.range(0, 3)
                        .forEach(i -> assertEquals(i, result.get(i).getId()));
    }

    @Test
    @DisplayName("여정 단건 조회 - 결과 없음")
    void findItinerary_notExist() {
        // when
        // then
        assertThrows(
                TravelDoesNotExistException.class,
                () -> itineraryService.findItinerary(CSV, 0L)
        );
    }

    @Test
    @DisplayName("여정 단건 조회")
    void findItinerary() {
        // given
        saveItinerary(0);

        // when
        Itinerary result = itineraryService.findItinerary(CSV, 0L);

        // then
        assertEqualsItinerary(0, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3})
    @DisplayName("여행으로 여정 조회")
    void findItineraries(int size) {
        // given
        IntStream.range(0, size)
                .forEach(this::saveItinerary);

        // when
        List<Itinerary> result = itineraryService.findItineraries(CSV, trip.getId());

        // then
        assertEquals(size, result.size());
        IntStream.range(0, size)
                .forEach(i -> assertEqualsItinerary(i, result.get(i)));
    }

    private void assertEqualsItinerary(int index, Itinerary itinerary) {
        Assertions.assertAll(
                () -> assertEquals(index, itinerary.getId()),
                () -> assertEquals(DEPARTURE + index, itinerary.getRoute().getDeparture()),
                () -> assertEquals(DESTINATION + index, itinerary.getRoute().getDestination()),
                () -> assertEquals(NOW.plusDays(index), itinerary.getRoute().getDepartureAt()),
                () -> assertEquals(NOW.plusDays(index).plusHours(3), itinerary.getRoute().getArriveAt()),
                () -> assertEquals(ACCOMMODATION + index, itinerary.getLodge().getAccommodation()),
                () -> assertEquals(NOW.plusDays(index), itinerary.getLodge().getCheckInAt()),
                () -> assertEquals(NOW.plusDays(index).plusHours(15), itinerary.getLodge().getCheckOutAt())
        );
    }

    private ItinerarySaveRequest createItinerarySaveRequest(int index) {
        return ItinerarySaveRequest.builder()
                .departure(DEPARTURE + index)
                .destination(DESTINATION + index)
                .departureAt(NOW.plusDays(index))
                .arriveAt(NOW.plusDays(index).plusHours(3))
                .accommodation(ACCOMMODATION + index)
                .checkInAt(NOW.plusDays(index))
                .checkOutAt(NOW.plusDays(index).plusHours(15))
                .build();
    }

    private void saveItinerary(int index) {
        Itinerary itinerary = createItinerary(index);
        itineraryRepository.save(itinerary);
    }

    private Itinerary createItinerary(int index) {
        return Itinerary.builder()
                .departure(DEPARTURE + index)
                .destination(DESTINATION + index)
                .departureAt(NOW.plusDays(index))
                .arriveAt(NOW.plusDays(index).plusHours(3))
                .accommodation(ACCOMMODATION + index)
                .checkInAt(NOW.plusDays(index))
                .checkOutAt(NOW.plusDays(index).plusHours(15))
                .trip(trip)
                .build();
    }
}
