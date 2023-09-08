package kr.co.fastcampus.travel.service;

import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.ItineraryRepositoryImpl;

public class ItineraryService {

    private final TripService tripService = new TripService();
    private final ItineraryRepositoryImpl itineraryRepository = new ItineraryRepositoryImpl();

    public Itinerary findItinerary(Long id) {
        return null;
    }

    public List<Itinerary> findItineraries(Long tripId) {
        return null;
    }

    public List<Itinerary> saveItineraries(Long tripId, List<ItinerarySaveRequest> itinerarySaveRequests) {
        Trip trip = tripService.findTrip(tripId);
        List<Itinerary> itineraries = new ArrayList<>();
        for (ItinerarySaveRequest itinerarySaveRequest : itinerarySaveRequests) {
            itineraries.add(
                    itineraryRepository.save(convertDtoToItinerary(trip, itinerarySaveRequest))
            );
        }
        return itineraries;
    }

    private Itinerary convertDtoToItinerary(Trip trip, ItinerarySaveRequest itinerarySaveRequest) {
        return Itinerary.builder()
                .trip(trip)
                .departure(itinerarySaveRequest.departure())
                .destination(itinerarySaveRequest.destination())
                .departureAt(itinerarySaveRequest.departureAt())
                .arriveAt(itinerarySaveRequest.arriveAt())
                .accommodation(itinerarySaveRequest.accommodation())
                .checkInAt(itinerarySaveRequest.checkInAt())
                .checkOutAt(itinerarySaveRequest.checkOutAt())
                .build();
    }
}
