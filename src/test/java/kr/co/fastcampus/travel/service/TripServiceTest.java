package kr.co.fastcampus.travel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.TripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class TripServiceTest {

    @Test
    @DisplayName("saveTrip()은 여행과 여정을 같이 저장한다")
    void saveTrip_should_save_trip_and_itineraries() {
        // given
        Long id = 1L;
        String name = "여행지 이름";
        LocalDate startAt = LocalDate.of(2023, 1, 1);
        LocalDate endAt = LocalDate.of(2023, 1, 2);
        String departure = "출발지";
        String destination = "도착지";
        String accommodation = "숙박지";
        LocalDateTime checkInAt = LocalDateTime.of(2023, 1, 1, 20, 20);
        LocalDateTime checkOutAt = LocalDateTime.of(2023, 1, 1, 20, 30);

        TripRepository mockTripRepo = Mockito.mock(TripRepository.class);
        when(mockTripRepo.save(any(Trip.class)))
            .thenAnswer(invocation -> {
                Trip tripToSave = invocation.getArgument(0);
                tripToSave.setId(id);
                return tripToSave;
            });

        // TODO ItineraryService.saveItineraries() 구현되면 mock 해제
        ItineraryService mockItinerarySvc = Mockito.mock(ItineraryService.class);
        when(mockItinerarySvc.saveItineraries(any(Long.class), any(List.class)))
            .thenAnswer(invocation -> {
               Itinerary itinerary = Itinerary.builder()
                   .departure(departure)
                   .destination(destination)
                   .accommodation(accommodation)
                   .checkInAt(checkInAt)
                   .checkOutAt(checkOutAt)
                   .build();
               return List.of(itinerary);
            });

        TripService tripService = new TripService(mockItinerarySvc, mockTripRepo);

        TripSaveRequest tripSaveRequest = TripSaveRequest.builder()
            .name(name)
            .startAt(startAt)
            .endAt(endAt)
            .itinerarySaveRequests(
                List.of(ItinerarySaveRequest.builder()
                    .departure(departure)
                    .destination(destination)
                    .accommodation(accommodation)
                    .checkInAt(checkInAt)
                    .checkOutAt(checkOutAt)
                    .build()
                )
            ).build();

        // when
        Trip savedTrip = tripService.saveTrip(tripSaveRequest);

        // then
        assertEquals(savedTrip.getId(), id);
        assertEquals(savedTrip.getName(), name);
        assertEquals(savedTrip.getStartAt(), startAt);
        assertEquals(savedTrip.getEndAt(), endAt);
        assertEquals(savedTrip.getItineraries().size(), 1);

        Itinerary itinerary = savedTrip.getItineraries().get(0);
        assertEquals(itinerary.getRoute().getDeparture(), departure);
        assertEquals(itinerary.getRoute().getDestination(), destination);
        assertEquals(itinerary.getLodge().getAccommodation(), accommodation);
        assertEquals(itinerary.getLodge().getCheckInAt(), checkInAt);
        assertEquals(itinerary.getLodge().getCheckOutAt(), checkOutAt);
    }
}
