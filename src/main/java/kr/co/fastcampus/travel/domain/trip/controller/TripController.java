package kr.co.fastcampus.travel.domain.trip.controller;

import java.util.ArrayList;
import java.util.List;

import kr.co.fastcampus.travel.domain.trip.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.domain.trip.controller.dto.TripResponse;
import kr.co.fastcampus.travel.domain.trip.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryService;
import kr.co.fastcampus.travel.domain.trip.service.TripService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    public List<TripInfoResponse> getTripList() {
        List<Trip> trips = tripService.findAllTrips();

        List<TripInfoResponse> tripInfoResponses = new ArrayList<>();
        for (Trip trip : trips) {
            TripInfoResponse tripInfoResponse = new TripInfoResponse(trip);
            tripInfoResponses.add(tripInfoResponse);
        }

        return tripInfoResponses;
    }

    public TripResponse findTrip(Long id) {
        Trip trip = tripService.getTrip(id);

        return new TripResponse(trip);
    }

    public void saveTrip(TripSaveRequest saveRequest) {
        tripService.saveTrip(saveRequest);
    }
}
