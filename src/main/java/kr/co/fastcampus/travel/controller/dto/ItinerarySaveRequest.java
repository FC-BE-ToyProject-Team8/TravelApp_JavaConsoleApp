package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDateTime;

public record ItinerarySaveRequest(String departure,
                                   String destination,
                                   LocalDateTime departureAt,
                                   LocalDateTime arriveAt,
                                   String accommodation,
                                   LocalDateTime checkInAt,
                                   LocalDateTime checkOutAt
) {

}
