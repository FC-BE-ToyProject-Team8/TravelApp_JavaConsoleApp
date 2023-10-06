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
import kr.co.fastcampus.travel.view.enums.FileType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelController {

    private final TripService tripService;
    private final ItineraryService itineraryService;

    public List<TripInfoResponse> getTripList(FileType fileType) {
        List<Trip> trips = tripService.findAllTrips(fileType);
        return trips.stream()
                .map(TripInfoResponse::of)
                .collect(Collectors.toList());
    }

    public TripResponse findTrip(FileType fileType, Long id) {
        Trip trip = tripService.findTrip(fileType, id);
        return TripResponse.of(trip);
    }

    public void saveTrip(TripSaveRequest request) {
        Trip savedTrip = tripService.saveTrip(request);
        itineraryService.saveItineraries(savedTrip.getId(), request.itinerarySaveRequests());
    }

    public List<ItineraryInfoResponse> getItineraryList(FileType fileType, Long tripId) {
        List<Itinerary> itineraries = itineraryService.findItineraries(fileType, tripId);
        return itineraries.stream()
                .map(ItineraryInfoResponse::of)
                .collect(Collectors.toList());
    }

    public ItineraryResponse findItinerary(FileType fileType, Long id) {
        Itinerary itinerary = itineraryService.findItinerary(fileType, id);
        return ItineraryResponse.of(itinerary);
    }

    public void saveItineraries(Long tripId, List<ItinerarySaveRequest> request) {
        itineraryService.saveItineraries(tripId, request);
    }
}
