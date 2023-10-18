package kr.co.fastcampus.travel.domain.itinerary.controller;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryService;
import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryInfoResponse;
import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryResponse;
import kr.co.fastcampus.travel.domain.itinerary.dto.request.ItinerarySaveRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.co.fastcampus.travel.domain.itinerary.entity.util.EntityToDtoConverter.*;

@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;

    public List<ItineraryInfoResponse> getItineraryList(Long tripId) {
        List<Itinerary> itineraries = itineraryService.findItineraries(tripId);
        return toItineraryInfoResponses(itineraries);
    }

    public ItineraryResponse findItinerary(Long id) {
        Itinerary itinerary = itineraryService.findItinerary(id);
        return toItineraryResponse(itinerary);
    }

    public void saveItineraries(Long tripId, List<ItinerarySaveRequest> saveRequests) {
        itineraryService.saveItineraries(tripId, saveRequests);
    }
}
