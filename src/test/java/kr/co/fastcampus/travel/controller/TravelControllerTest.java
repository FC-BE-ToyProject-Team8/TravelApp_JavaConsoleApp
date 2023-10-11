package kr.co.fastcampus.travel.controller;

import static kr.co.fastcampus.travel.common.ItineraryUtils.ACCOMMODATION;
import static kr.co.fastcampus.travel.common.ItineraryUtils.DEPARTURE;
import static kr.co.fastcampus.travel.common.ItineraryUtils.DESTINATION;
import static kr.co.fastcampus.travel.common.ItineraryUtils.NOW;
import static kr.co.fastcampus.travel.common.ItineraryUtils.createItinerary;
import static kr.co.fastcampus.travel.common.TripUtils.TODAY;
import static kr.co.fastcampus.travel.common.TripUtils.TRIP_NAME;
import static kr.co.fastcampus.travel.common.TripUtils.createTrip;
import static kr.co.fastcampus.travel.view.enums.FileType.CSV;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kr.co.fastcampus.travel.common.ItineraryUtils;
import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.controller.dto.TripResponse;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.mock.FakeItineraryRepository;
import kr.co.fastcampus.travel.mock.FakeTripRepository;
import kr.co.fastcampus.travel.repository.ItineraryRepository;
import kr.co.fastcampus.travel.repository.TripRepository;
import kr.co.fastcampus.travel.service.ItineraryService;
import kr.co.fastcampus.travel.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("여행 컨트롤러 테스트")
class TravelControllerTest {

    private TravelController travelController;
    private TripRepository tripRepository;
    private ItineraryRepository itineraryRepository;

    private Trip trip;

    @BeforeEach
    void setup() {
        tripRepository = new FakeTripRepository();
        itineraryRepository = new FakeItineraryRepository();
        
        TripService tripService = new TripService(tripRepository);
        travelController = new TravelController(
                tripService, 
                new ItineraryService(tripService, itineraryRepository)
        );

        trip = tripRepository.save(createTrip(0));
    }

    @Test
    @DisplayName("여행과 여정 저장")
    void saveTrip() {
        // given
        TripSaveRequest request = new TripSaveRequest(
                TRIP_NAME,
                TODAY,
                TODAY.plusDays(3),
                IntStream.range(0, 3)
                        .mapToObj(ItineraryUtils::createItinerarySaveRequest)
                        .collect(Collectors.toList())
        );

        // when
        travelController.saveTrip(request);

        // then
        Trip findTrip = tripRepository.findById(CSV, 1L)
                .orElse(null);
        assertNotNull(findTrip);
        List<Itinerary> findItineraries = itineraryRepository.findByTrip(CSV, findTrip);
        assertEquals(3, findItineraries.size());
    }

    @Test
    @DisplayName("모든 여행 조회")
    void getTripList() {
        // given
        IntStream.range(0, 3)
                .forEach(this::saveTrip);

        // when
        List<TripInfoResponse> result = travelController.getTripList(CSV);

        // then
        assertEquals(4, result.size());
    }

    @Test
    @DisplayName("여행 단건 조회")
    void findTrip() {
        // given
        // when
        TripResponse result = travelController.findTrip(CSV, trip.getId());

        // then
        assertAll(
                () -> assertEquals(0L, result.id()),
                () -> assertEquals(TRIP_NAME + 0, result.name()),
                () -> assertEquals(TODAY, result.startAt()),
                () -> assertEquals(TODAY.plusDays(3), result.endAt())
        );
    }

    @Test
    @DisplayName("여정 목록 저장")
    void saveItineraries() {
        // given
        List<ItinerarySaveRequest> request = IntStream.range(0, 3)
                .mapToObj(ItineraryUtils::createItinerarySaveRequest)
                .collect(Collectors.toList());

        // when
        travelController.saveItineraries(trip.getId(), request);

        // then
        List<Itinerary> findItineraries = itineraryRepository.findByTrip(CSV, trip);
        assertEquals(3, findItineraries.size());
    }

    @Test
    @DisplayName("여행에 포함된 모든 여정 조회")
    void getItineraryList() {
        // given
        IntStream.range(0, 3)
                .forEach(this::saveItinerary);

        // when
        List<ItineraryInfoResponse> result = travelController.getItineraryList(CSV, trip.getId());

        // then
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("여정 단건 조회")
    void findItinerary() {
        // given
        saveItinerary(0);

        // when
        ItineraryResponse result = travelController.findItinerary(CSV, 0L);

        // then
        assertAll(
                () -> assertEquals(0L, result.id()),
                () -> assertEquals(DEPARTURE + 0, result.departure()),
                () -> assertEquals(DESTINATION + 0, result.destination()),
                () -> assertEquals(NOW, result.departureAt()),
                () -> assertEquals(NOW.plusHours(3), result.arriveAt()),
                () -> assertEquals(ACCOMMODATION + 0, result.accommodation()),
                () -> assertEquals(NOW, result.checkInAt()),
                () -> assertEquals(NOW.plusHours(15), result.checkOutAt())
        );
    }

    private void saveTrip(int index) {
        Trip trip = createTrip(index);
        tripRepository.save(trip);
    }

    public void saveItinerary(int index) {
        Itinerary itinerary = createItinerary(index, trip);
        itineraryRepository.save(itinerary);
    }
}
