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

public class TravelDtoConverter {

    private TravelDtoConverter() {
    }

    public static List<TripInfoResponse> toTripInfoResponseList(List<Trip> trips) {
        return trips.stream()
                .map(TravelDtoConverter::toTripInfoResponse)
                .collect(Collectors.toList());
    }

    private static TripInfoResponse toTripInfoResponse(Trip trip) {
        return TripInfoResponse.builder()
                .id(trip.getId())
                .name(trip.getName())
                .startAt(trip.getStartAt())
                .endAt(trip.getEndAt())
                .build();
    }

    public static TripResponse toTripResponse(Trip trip) {
        return TripResponse.builder()
                .id(trip.getId())
                .name(trip.getName())
                .startAt(trip.getStartAt())
                .endAt(trip.getEndAt())
                .itineraries(toItineraryResponseList(trip.getItineraries()))
                .build();
    }

    private static List<ItineraryResponse> toItineraryResponseList(List<Itinerary> itineraries) {
        return itineraries.stream()
                .map(TravelDtoConverter::toItineraryResponse)
                .collect(Collectors.toList());
    }

    public static Trip toTrip(TripSaveRequest request) {
        Trip trip = Trip.builder()
                .name(request.name())
                .startAt(request.startAt())
                .endAt(request.endAt())
                .build();
        addItineraries(request.itinerarySaveRequests(), trip);
        return trip;
    }

    private static void addItineraries(List<ItinerarySaveRequest> request, Trip trip) {
        request.forEach(itineraryRequest -> {
            Itinerary itinerary = toItinerary(itineraryRequest);
            itinerary.setTrip(trip);
        });
    }

    public static List<ItineraryInfoResponse> toItineraryInfoResponseList(List<Itinerary> itineraries) {
        return itineraries.stream()
                .map(TravelDtoConverter::toItineraryInfoResponse)
                .collect(Collectors.toList());
    }

    private static ItineraryInfoResponse toItineraryInfoResponse(Itinerary itinerary) {
        return ItineraryInfoResponse.builder()
                .id(itinerary.getId())
                .destination(itinerary.getRoute().getDestination())
                .departure(itinerary.getRoute().getDeparture())
                .build();
    }

    public static ItineraryResponse toItineraryResponse(Itinerary itinerary) {
        return ItineraryResponse.builder()
                .id(itinerary.getId())
                .departure(itinerary.getRoute().getDeparture())
                .destination(itinerary.getRoute().getDestination())
                .departureAt(itinerary.getRoute().getDepartureAt())
                .arriveAt(itinerary.getRoute().getArriveAt())
                .accommodation(itinerary.getLodge().getAccommodation())
                .checkInAt(itinerary.getLodge().getCheckInAt())
                .checkOutAt(itinerary.getLodge().getCheckOutAt())
                .build();
    }

    public static List<Itinerary> toItineraryList(List<ItinerarySaveRequest> requests) {
        return requests.stream()
                .map(TravelDtoConverter::toItinerary)
                .collect(Collectors.toList());
    }

    private static Itinerary toItinerary(ItinerarySaveRequest request) {
        return Itinerary.builder()
                .departure(request.departure())
                .destination(request.destination())
                .departureAt(request.departureAt())
                .arriveAt(request.arriveAt())
                .accommodation(request.accommodation())
                .checkInAt(request.checkInAt())
                .checkOutAt(request.checkOutAt())
                .build();
    }
}
