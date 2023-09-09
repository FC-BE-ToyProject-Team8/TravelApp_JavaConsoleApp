package kr.co.fastcampus.travel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.controller.dto.TripResponse;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.service.ItineraryService;
import kr.co.fastcampus.travel.service.TripService;

public class TravelController {

    private final ItineraryService itineraryService;
    private final TripService tripService;

    public TravelController() {
        itineraryService = new ItineraryService();
        tripService = new TripService();
    }

    public List<TripInfoResponse> getTripList(FileType fileType) {

        List<Trip> trips = tripService.findAllTrips(fileType);
        if (trips.isEmpty()) {
            throw new TravelDoesNotExistException();
        }

        List<TripInfoResponse> tripInfoResponses = new ArrayList<>();
        for (int i = 0; i < trips.size(); i++) {
            TripInfoResponse tripInfoResponse = new TripInfoResponse(trips.get(i));
            tripInfoResponses.add(tripInfoResponse);
        }

        return tripInfoResponses;
    }

    public TripResponse findTrip(FileType fileType, Long id) {

        Trip trip = tripService.findTrip(fileType, id);
        List<Itinerary> itineraries = trip.getItineraries();
        if (trip == null) {
            throw new TravelDoesNotExistException();
        }

        return new TripResponse(trip, itineraries);
    }

    public void saveTrip(TripSaveRequest saveRequest) {
        Trip savedTrip = tripService.saveTrip(saveRequest);
        itineraryService.saveItineraries(savedTrip.getId(), saveRequest.itinerarySaveRequests());
    }

    public List<ItineraryInfoResponse> getItineraryList(FileType fileType, Long tripId) {
        List<Itinerary> response = itineraryService.findItineraries(fileType, tripId);
        return response.stream()
                .map(ItineraryInfoResponse::new)
                .collect(Collectors.toList());
    }

    public ItineraryResponse findItinerary(FileType fileType, Long id) {
        Itinerary response = itineraryService.findItinerary(fileType, id);
        if (response == null) {
            throw new TravelDoesNotExistException();
        }
        return new ItineraryResponse(response);
    }

    public void saveItineraries(Long tripId, List<ItinerarySaveRequest> saveRequests) {
        itineraryService.saveItineraries(tripId, saveRequests);
    }
}
