package kr.co.fastcampus.travel.domain.itinerary.controller;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryService;
import kr.co.fastcampus.travel.domain.trip.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.domain.trip.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.domain.trip.controller.dto.ItinerarySaveRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;

    public List<ItineraryInfoResponse> getItineraryList(Long tripId) {
        List<Itinerary> response = itineraryService.findItineraries(tripId);
        return response.stream()
                .map(ItineraryInfoResponse::new)
                .collect(Collectors.toList());
    }

    public ItineraryResponse findItinerary(Long id) {
        Itinerary response = itineraryService.findItinerary(id);
        return new ItineraryResponse(response);
    }

    public void saveItineraries(Long tripId, List<ItinerarySaveRequest> saveRequests) {
        itineraryService.saveItineraries(tripId, saveRequests);
    }
}
