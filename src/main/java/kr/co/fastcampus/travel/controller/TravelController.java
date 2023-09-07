package kr.co.fastcampus.travel.controller;

import java.util.ArrayList;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.*;

import java.util.List;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.service.TripService;

public class TravelController {

    private TripService tripService;

    public List<TripInfoResponse> getTripList(FileType fileType) {

        List<Trip> trips = tripService.findAllTrips(fileType);

        if (trips.isEmpty()) {
            throw new TravelDoesNotExistException();
        }

        List<TripInfoResponse> tripInfoResponses = new ArrayList<>();
        for (int i = 0; i < trips.size(); i++) {
            TripInfoResponse tripInfoResponse = new TripInfoResponse(
                trips.get(i).getId(),
                trips.get(i).getName(),
                trips.get(i).getStartAt(),
                trips.get(i).getEndAt()
            );

            tripInfoResponses.add(tripInfoResponse);
        }

        return tripInfoResponses;
    }

    public TripResponse findTrip(FileType fileType, Long id) {

        Trip trip = tripService.findTrip(fileType, id);
        if (trip == null) {
            throw new TravelDoesNotExistException();
        }

        TripResponse tripResponse = new TripResponse(
            trip.getId(),
            trip.getName(),
            trip.getStartAt(),
            trip.getEndAt(),
            trip.getItineraries()
        );

        return tripResponse;
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
