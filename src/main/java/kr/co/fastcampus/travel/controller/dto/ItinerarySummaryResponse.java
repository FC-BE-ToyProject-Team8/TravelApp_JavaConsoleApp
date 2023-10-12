package kr.co.fastcampus.travel.controller.dto;

import lombok.Builder;

@Builder
public record ItinerarySummaryResponse(
        Long id,
        String departure,
        String destination
) {

}
