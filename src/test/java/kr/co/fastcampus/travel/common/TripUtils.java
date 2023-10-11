package kr.co.fastcampus.travel.common;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import kr.co.fastcampus.travel.domain.Trip;

public class TripUtils {

    public static final String TRIP_NAME = "여행";
    public static final LocalDate TODAY = LocalDate.now();

    static public void assertEqualsTrip(int index, Trip result) {
        assertAll(
                () -> assertEquals(index, result.getId()),
                () -> assertEquals(TRIP_NAME + index, result.getName()),
                () -> assertEquals(TODAY.plusDays(index), result.getStartAt()),
                () -> assertEquals(TODAY.plusDays(index + 3), result.getEndAt())
        );
    }

    static public Trip createTrip(int index) {
        return Trip.builder()
                .name(TRIP_NAME + index)
                .startAt(TODAY.plusDays(index))
                .endAt(TODAY.plusDays(index + 3))
                .build();
    }
}
