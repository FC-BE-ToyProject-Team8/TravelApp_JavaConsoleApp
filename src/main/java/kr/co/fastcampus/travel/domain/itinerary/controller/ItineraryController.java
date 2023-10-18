package kr.co.fastcampus.travel.domain.itinerary.controller;

import kr.co.fastcampus.travel.domain.itinerary.entity.Itinerary;
import kr.co.fastcampus.travel.domain.itinerary.entity.util.EntityToDtoConverter;
import kr.co.fastcampus.travel.domain.itinerary.service.ItineraryService;
import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryInfoResponse;
import kr.co.fastcampus.travel.domain.itinerary.dto.response.ItineraryResponse;
import kr.co.fastcampus.travel.domain.itinerary.dto.request.ItinerarySaveRequest;
import kr.co.fastcampus.travel.domain.trip.dto.util.DtoToEntityConverter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static kr.co.fastcampus.travel.domain.itinerary.entity.util.EntityToDtoConverter.toItineraryResponse;

@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;

    public List<ItineraryInfoResponse> getItineraryList(Long tripId) {
        List<Itinerary> response = itineraryService.findItineraries(tripId);
        return response.stream()
                .map(EntityToDtoConverter::toItineraryInfoResponse)
                .collect(Collectors.toList());
    }

    public ItineraryResponse findItinerary(Long id) {
        Itinerary itinerary = itineraryService.findItinerary(id);
        return toItineraryResponse(itinerary);
    }

    public void saveItineraries(Long tripId, List<ItinerarySaveRequest> saveRequests) {
        itineraryService.saveItineraries(tripId, saveRequests);
    }
}
