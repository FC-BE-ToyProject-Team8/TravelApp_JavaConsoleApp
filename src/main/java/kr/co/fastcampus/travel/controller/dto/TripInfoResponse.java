package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;

public record TripInfoResponse(Long id,
                               String name,
                               LocalDate startAt,
                               LocalDate endAt
) {

}
