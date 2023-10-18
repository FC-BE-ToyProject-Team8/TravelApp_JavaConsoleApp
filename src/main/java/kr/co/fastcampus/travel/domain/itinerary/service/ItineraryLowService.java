package kr.co.fastcampus.travel.domain.itinerary.service;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.repository.ItineraryRepository;
import kr.co.fastcampus.travel.domain.trip.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.trip.entity.Trip;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ItineraryLowService {

    private final ItineraryRepository itineraryRepository;

    public Optional<Itinerary> findById(Long id) {
        return itineraryRepository.findById(id);
    }

    public List<Itinerary> findByTrip(Trip trip) {
        return itineraryRepository.findByTrip(trip);
    }

    public Itinerary save(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    public List<Itinerary> saveAllByRequests(Trip trip, List<ItinerarySaveRequest> itinerarySaveRequests) {
        List<Itinerary> itineraries = new ArrayList<>();
        for (ItinerarySaveRequest request : itinerarySaveRequests) {
            Itinerary itinerary = Itinerary.builder()
                    .trip(trip)
                    .departure(request.departure())
                    .destination(request.destination())
                    .departureAt(request.departureAt())
                    .arriveAt(request.arriveAt())
                    .accommodation(request.departure())
                    .checkInAt(request.checkInAt())
                    .checkOutAt(request.checkOutAt())
                    .build();
            Itinerary savedItinerary = save(itinerary);
            itineraries.add(savedItinerary);
        }
        return itineraries;
    }
}
