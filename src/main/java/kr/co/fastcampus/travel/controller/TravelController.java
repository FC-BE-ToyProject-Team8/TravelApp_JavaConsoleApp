package kr.co.fastcampus.travel.controller;

import static kr.co.fastcampus.travel.controller.TravelDtoConverter.*;

import java.util.List;
import kr.co.fastcampus.travel.common.response.CommonResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySummaryResponse;
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

    public CommonResponse<List<TripInfoResponse>> getTripList() {
        List<Trip> trips = tripService.findAllTrips();
        return CommonResponse.success(toTripInfoResponseList(trips));
    }

    public CommonResponse<TripResponse> findTrip(Long id) {
        Trip trip = tripService.findTrip(id);
        return CommonResponse.success(toTripResponse(trip));
    }

    public CommonResponse<String> saveTrip(TripSaveRequest request) {
        tripService.saveTrip(toTrip(request));
        return CommonResponse.success("저장에 성공하였습니다.");
    }

    public CommonResponse<List<ItinerarySummaryResponse>> getItineraryList(Long tripId) {
        Trip trip = tripService.findTrip(tripId);
        List<Itinerary> itineraries = itineraryService.findItineraries(trip);
        return CommonResponse.success(toItinerarySummaryResponseList(itineraries));
    }

    public CommonResponse<ItineraryResponse> findItinerary(Long id) {
        Itinerary itinerary = itineraryService.findItinerary(id);
        return CommonResponse.success(toItineraryResponse(itinerary));
    }

    public CommonResponse<String> saveItineraries(Long tripId, List<ItinerarySaveRequest> requests) {
        Trip trip = tripService.findTrip(tripId);
        List<Itinerary> itineraries = toItineraryList(requests);
        itineraryService.saveItineraries(trip, itineraries);
        return CommonResponse.success("저장에 성공하였습니다.");
    }
}
