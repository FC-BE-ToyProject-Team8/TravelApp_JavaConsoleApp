package kr.co.fastcampus.travel.domain.trip.service;

import java.util.List;

import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryLowService;
import kr.co.fastcampus.travel.domain.trip.dto.request.TripSaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TripService {

    private final TripLowService tripLowService;
    private final ItineraryLowService itineraryLowService;

    public List<Trip> findAllTrips() {
        List<Trip> trips = tripLowService.findAll();
        if (trips.isEmpty()) {
            throw new TravelDoesNotExistException();
        }
        return trips;
    }

    public Trip saveTrip(TripSaveRequest saveRequest) {
        Trip trip = saveRequest.toDomain();

        List<Itinerary> itineraries = itineraryLowService.saveAllByRequests(trip, saveRequest.itinerarySaveRequests());;
        trip.setItineraries(itineraries);

        return tripLowService.save(trip);
    }

    public Trip getTrip(Long id) {
        Trip trip = tripLowService.findTrip(id);

        List<Itinerary> itineraries = itineraryLowService.findByTrip(trip);
        trip.setItineraries(itineraries);

        return trip;
    }
}
