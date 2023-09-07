package kr.co.fastcampus.travel.controller;

import kr.co.fastcampus.travel.controller.dto.*;

import java.util.List;

public class TravelController {

    public List<TripInfoResponse> getTripList() {
        return null;
    }

    public TripResponse findTrip(Long id) {
        return null;
    }

    public String saveTrips(TripSaveRequest saveRequest) {
        return null;
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
