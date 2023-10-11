package kr.co.fastcampus.travel.controller;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.controller.dto.TripResponse;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.service.ItineraryService;
import kr.co.fastcampus.travel.service.TripService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelController {

    private final TripService tripService;
    private final ItineraryService itineraryService;

    public List<TripInfoResponse> getTripList() {
        List<Trip> trips = tripService.findAllTrips();
        return trips.stream()
                .map(TripInfoResponse::of)
                .collect(Collectors.toList());
    }

    public TripResponse findTrip(Long id) {
        Trip trip = tripService.findTrip(id);
        return TripResponse.of(trip);
    }

    public void saveTrip(TripSaveRequest request) {
        tripService.saveTrip(request.toDomain());
    }

    public List<ItineraryInfoResponse> getItineraryList(Long tripId) {
        Trip trip = tripService.findTrip(tripId);
        List<Itinerary> itineraries = itineraryService.findItineraries(trip);
        return itineraries.stream()
                .map(ItineraryInfoResponse::of)
                .collect(Collectors.toList());
    }

    public ItineraryResponse findItinerary(Long id) {
        Itinerary itinerary = itineraryService.findItinerary(id);
        return ItineraryResponse.of(itinerary);
    }

    public void saveItineraries(Long tripId, List<ItinerarySaveRequest> requests) {
        Trip trip = tripService.findTrip(tripId);
        List<Itinerary> itineraries = requests.stream()
                .map(ItinerarySaveRequest::toDomain)
                .collect(Collectors.toList());
        itineraryService.saveItineraries(trip, itineraries);
    }
}
