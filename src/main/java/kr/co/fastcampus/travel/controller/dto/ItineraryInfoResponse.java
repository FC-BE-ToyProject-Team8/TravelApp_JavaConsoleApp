package kr.co.fastcampus.travel.controller.dto;

import lombok.Builder;

@Builder
public record ItineraryInfoResponse(
        Long id,
        String departure,
        String destination
) {

}
