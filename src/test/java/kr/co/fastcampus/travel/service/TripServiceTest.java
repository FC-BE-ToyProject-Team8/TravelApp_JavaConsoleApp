package kr.co.fastcampus.travel.service;

import static kr.co.fastcampus.travel.common.TripUtils.TODAY;
import static kr.co.fastcampus.travel.common.TripUtils.TRIP_NAME;
import static kr.co.fastcampus.travel.common.TripUtils.assertEqualsTrip;
import static kr.co.fastcampus.travel.common.TripUtils.createTrip;
import static kr.co.fastcampus.travel.view.enums.FileType.CSV;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.IntStream;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.mock.FakeTripRepository;
import kr.co.fastcampus.travel.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("여행 서비스 테스트")
class TripServiceTest {

    private TripRepository tripRepository;
    private TripService tripService;

    @BeforeEach
    void setup() {
        tripRepository = new FakeTripRepository();
        tripService = new TripService(tripRepository);
    }

    @Test
    @DisplayName("여행 저장")
    void saveTrip() {
        // given
        TripSaveRequest request = new TripSaveRequest(
                TRIP_NAME,
                TODAY,
                TODAY.plusDays(3),
                List.of()
        );

        // when
        Trip result = tripService.saveTrip(request);

        // then
        assertEquals(0L, result.getId());
    }

    @Test
    @DisplayName("모든 여행 조회")
    void findAllTrips() {
        // given
        IntStream.range(0, 3)
                .forEach(this::saveTrip);

        // when
        List<Trip> result = tripService.findAllTrips(CSV);

        // then
        assertEquals(3, result.size());
        IntStream.range(0, 3)
                .forEach(i -> assertEqualsTrip(i, result.get(i)));
    }

    @Test
    @DisplayName("여행 단건 조회 - 결과 없음")
    void findTrip_notExist() {
        // when
        // then
        assertThrows(
                TravelDoesNotExistException.class,
                () -> tripService.findTrip(CSV, 0L)
        );
    }

    @Test
    @DisplayName("여행 단건 조회")
    void findTrip() {
        // given
        saveTrip(0);

        // when
        Trip result = tripService.findTrip(CSV, 0L);

        // then
        assertEqualsTrip(0, result);
    }

    private void saveTrip(int index) {
        Trip trip = createTrip(index);
        tripRepository.save(trip);
    }
}