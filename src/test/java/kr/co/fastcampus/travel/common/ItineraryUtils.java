package kr.co.fastcampus.travel.common;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;

public class ItineraryUtils {

    public static final String DEPARTURE = "출발지";
    public static final String DESTINATION = "목적지";
    public static final String ACCOMMODATION = "숙소";
    public static final LocalDateTime NOW = LocalDateTime.now();

    public static ItinerarySaveRequest createItinerarySaveRequest(int index) {
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

    public static void assertEqualsItinerary(int index, Itinerary itinerary) {
        assertAll(
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

    public static Itinerary createItinerary(int index, Trip trip) {
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
