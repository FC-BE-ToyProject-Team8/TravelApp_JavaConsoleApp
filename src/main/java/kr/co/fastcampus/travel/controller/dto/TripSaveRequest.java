package kr.co.fastcampus.travel.controller.dto;

import java.time.LocalDate;

public record TripSaveRequest(String name, LocalDate startAt, LocalDate endAt) {

}
