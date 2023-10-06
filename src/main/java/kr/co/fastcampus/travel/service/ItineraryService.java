package kr.co.fastcampus.travel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.Itinerary;
import kr.co.fastcampus.travel.domain.Trip;
import kr.co.fastcampus.travel.repository.ItineraryRepository;
import kr.co.fastcampus.travel.view.enums.FileType;
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

    public List<Itinerary> saveItineraries(
            Long tripId,
            List<ItinerarySaveRequest> itinerarySaveRequests
    ) {
        Trip trip = tripService.findTrip(FileType.CSV, tripId);
        return itinerarySaveRequests.stream()
                .map(request -> {
                    Itinerary itinerary = request.toDomain(trip);
                    return itineraryRepository.save(itinerary);
                })
                .collect(Collectors.toList());
    }
}
