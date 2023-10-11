package kr.co.fastcampus.travel.service;

import static kr.co.fastcampus.travel.common.ItineraryUtils.assertEqualsItinerary;
import static kr.co.fastcampus.travel.common.ItineraryUtils.createItinerary;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import kr.co.fastcampus.travel.common.ItineraryUtils;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.mock.FakeItineraryRepository;
import kr.co.fastcampus.travel.mock.FakeTripRepository;
import kr.co.fastcampus.travel.repository.ItineraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("여정 서비스 테스트")
class ItineraryServiceTest {

    private Trip trip;

    private ItineraryService itineraryService;
    private ItineraryRepository itineraryRepository;

    @BeforeEach
    void setup() {
        FakeTripRepository tripRepository = new FakeTripRepository();
        itineraryRepository = new FakeItineraryRepository();
        itineraryService = new ItineraryService(itineraryRepository);

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
                .mapToObj(ItineraryUtils::createItinerarySaveRequest)
                .toList();

        // when
        List<Itinerary> result = itineraryService.saveItineraries(trip, request);

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
                () -> itineraryService.findItinerary(0L)
        );
    }

    @Test
    @DisplayName("여정 단건 조회")
    void findItinerary() {
        // given
        saveItinerary(0);

        // when
        Itinerary result = itineraryService.findItinerary(0L);

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
        List<Itinerary> result = itineraryService.findItineraries(trip);

        // then
        assertEquals(size, result.size());
        IntStream.range(0, size)
                .forEach(i -> assertEqualsItinerary(i, result.get(i)));
    }

    private void saveItinerary(int index) {
        Itinerary itinerary = createItinerary(index, trip);
        itineraryRepository.save(itinerary);
    }
}
