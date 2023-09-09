package kr.co.fastcampus.travel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.FileType;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.infrastructure.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItineraryService {

    private final TripService tripService;
    private final ItineraryRepository itineraryRepository;

    public Itinerary findItinerary(FileType fileType, Long id) {
        Optional<Itinerary> response = itineraryRepository.findById(fileType, id);
        return response.orElseThrow(TravelDoesNotExistException::new);
    }

    public List<Itinerary> findItineraries(FileType fileType, Long tripId) {
        Trip trip = tripService.findTrip(fileType, tripId);
        return itineraryRepository.findByTrip(fileType, trip);
    }

    public List<Itinerary> saveItineraries(Long tripId,
            List<ItinerarySaveRequest> itinerarySaveRequests) {
        Trip trip = tripService.findTrip(FileType.CSV, tripId);
        List<Itinerary> itineraries = new ArrayList<>();
        for (ItinerarySaveRequest itinerarySaveRequest : itinerarySaveRequests) {
            itineraries.add(
                    itineraryRepository.save(convertDtoToItinerary(trip, itinerarySaveRequest))
            );
        }
        return itineraries;
    }

    private Itinerary convertDtoToItinerary(Trip trip, ItinerarySaveRequest itinerarySaveRequest) {
        return Itinerary.builder()
                .trip(trip)
                .departure(itinerarySaveRequest.departure())
                .destination(itinerarySaveRequest.destination())
                .departureAt(itinerarySaveRequest.departureAt())
                .arriveAt(itinerarySaveRequest.arriveAt())
                .accommodation(itinerarySaveRequest.accommodation())
                .checkInAt(itinerarySaveRequest.checkInAt())
                .checkOutAt(itinerarySaveRequest.checkOutAt())
                .build();
    }
}
