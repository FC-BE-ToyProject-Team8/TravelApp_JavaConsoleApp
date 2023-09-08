package kr.co.fastcampus.travel.controller.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;

@Builder
public record ItineraryResponse(Long id,
								String departure,
								String destination,
								LocalDateTime departureAt,
								LocalDateTime arriveAt,
								String accommodation,
								LocalDateTime checkInAt,
								LocalDateTime checkOutAt) {

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append("출발 : ").append(departure).append(", ")
			.append(formatter.format(departureAt)).append("\n");
		sb.append("출발 : ").append(destination).append(", ")
			.append(formatter.format(arriveAt)).append("\n");
		sb.append("숙박 시설 : ").append(accommodation).append("\n");
		sb.append("체크인: ").append(formatter.format(checkInAt)).append("\n");
		sb.append("체크아웃: ").append(formatter.format(checkOutAt)).append("\n");
		return sb.toString();
	}
}
