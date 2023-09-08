package kr.co.fastcampus.travel.controller;

import kr.co.fastcampus.travel.controller.dto.*;

import java.util.List;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.service.TripService;

public class TravelController {

    private final TripService tripService;

    public TravelController() {
        tripService = new TripService();
    }

    public List<TripInfoResponse> getTripList() {
        return null;
    }

    public TripResponse findTrip(Long id) {
        return null;
    }

    public void saveTrip(TripSaveRequest saveRequest) {
        Trip savedTrip = tripService.saveTrip(saveRequest);

        saveItineraries(savedTrip.getId(), saveRequest.itinerarySaveRequests());
    }

    public List<ItineraryResponse> getItineraryList(Long tripId) {
        return null;
    }

    public ItineraryResponse findItinerary(Long id) {
        return null;
    }

    public String saveItineraries(Long tripId, List<ItinerarySaveRequest> saveRequests) {
        return null;
    }
}
