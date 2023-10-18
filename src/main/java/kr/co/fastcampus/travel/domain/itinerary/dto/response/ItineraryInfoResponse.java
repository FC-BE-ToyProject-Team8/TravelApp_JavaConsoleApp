package kr.co.fastcampus.travel.domain.itinerary.dto.response;


public record ItineraryInfoResponse(
    Long id,
    String departure,
    String destination
) {

}
