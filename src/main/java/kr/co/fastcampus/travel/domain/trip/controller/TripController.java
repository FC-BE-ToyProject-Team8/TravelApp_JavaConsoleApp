package kr.co.fastcampus.travel.domain.trip.controller;

import java.util.ArrayList;
import java.util.List;

import kr.co.fastcampus.travel.domain.trip.dto.response.TripInfoResponse;
import kr.co.fastcampus.travel.domain.trip.dto.response.TripResponse;
import kr.co.fastcampus.travel.domain.trip.dto.request.TripSaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import kr.co.fastcampus.travel.domain.trip.entity.util.EntityToDtoConverter;
import kr.co.fastcampus.travel.domain.trip.service.TripService;
import lombok.RequiredArgsConstructor;

import static kr.co.fastcampus.travel.domain.trip.entity.util.EntityToDtoConverter.*;

@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    public List<TripInfoResponse> getTripList() {
        List<Trip> trips = tripService.findAllTrips();
        return toTripInfoResponses(trips);
    }

    public TripResponse findTrip(Long id) {
        Trip trip = tripService.getTrip(id);
        return toTripResponse(trip);
    }

    public void saveTrip(TripSaveRequest saveRequest) {
        tripService.saveTrip(saveRequest);
    }
}
