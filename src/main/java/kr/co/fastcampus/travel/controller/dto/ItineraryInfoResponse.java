package kr.co.fastcampus.travel.controller.dto;

import lombok.Builder;

@Builder
public record ItineraryInfoResponse(Long id, String departure, String destination) {

	@Override
	public String toString() {
		return String.format("%d: %s ~ %s", id, departure, destination);
	}
}
