package kr.co.fastcampus.travel.domain.itinerary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.domain.itinerary.dto.request.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.trip.service.TripLowService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryLowService itineraryLowService;
    private final TripLowService tripLowService;

    public Itinerary findItinerary(Long id) {
        Optional<Itinerary> response = itineraryLowService.findById(id);
        return response.orElseThrow(TravelDoesNotExistException::new);
    }

    public List<Itinerary> findItineraries(Long tripId) {
        Trip trip = tripLowService.findTrip(tripId);
        return itineraryLowService.findByTrip(trip);
    }

    public List<Itinerary> saveItineraries(Long tripId,
            List<ItinerarySaveRequest> itinerarySaveRequests) {
        Trip trip = tripLowService.findTrip(tripId);

        List<Itinerary> itineraries = itineraryLowService.saveAllByRequests(trip, itinerarySaveRequests);
        return itineraries;
    }
}
