package kr.co.fastcampus.travel.controller;

import kr.co.fastcampus.travel.controller.dto.*;

import java.util.List;

public class TravelController {
    public List<TripNameResponse> getTripList() {
        return null;
    }

    public TripResponse findTrip() {
        return null;
    }

    public String saveTrips(TripSaveRequest saveRequest) {
        return null;
    }

    public List<ItineraryResponse> getItineraryList() {
        return null;
    }

    public ItineraryResponse findItinerary(Long id) {
        return null;
    }

    public String saveItineraries(Long tripId, List<ItinerarySaveRequest> saveRequests) {
        return null;
    }

    public List<ItineraryResponse> findItineraries(Long tripId) {
        return null;
    }

}
