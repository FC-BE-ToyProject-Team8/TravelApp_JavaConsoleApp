package kr.co.fastcampus.travel.service;

import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.ItineraryRepositoryImpl;
import java.util.List;

public class ItineraryService {

    private final TripService tripService = new TripService();
    private final ItineraryRepositoryImpl itineraryRepository = new ItineraryRepositoryImpl();

    public Itinerary findItinerary(Long id) {
        return null;
    }

    public List<Itinerary> findItineraries(Long tripId) {
        return null;
    }

    public void saveItineraries(Long tripId, List<ItinerarySaveRequest> itinerarySaveRequests) {
        Trip trip = tripService.findTrip(tripId);
        for (ItinerarySaveRequest itinerarySaveRequest : itinerarySaveRequests) {
            Itinerary itinerary = Itinerary.builder()
                    .trip(trip)
                    .departure(itinerarySaveRequest.departure())
                    .destination(itinerarySaveRequest.destination())
                    .departureAt(itinerarySaveRequest.departureAt())
                    .arriveAt(itinerarySaveRequest.arriveAt())
                    .accommodation(itinerarySaveRequest.accommodation())
                    .checkInAt(itinerarySaveRequest.checkInAt())
                    .checkOutAt(itinerarySaveRequest.checkOutAt())
                    .build();
            itineraryRepository.save(itinerary);
        }
    }
}
