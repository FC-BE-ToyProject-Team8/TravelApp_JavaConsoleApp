package kr.co.fastcampus.travel.controller.dto;

import java.util.stream.IntStream;
import kr.co.fastcampus.travel.domain.Itinerary;

import java.time.LocalDate;
import java.util.List;

//Itinerary 도메인 사용 수정 필요
public record TripResponse(Long id, String name, LocalDate startAt, LocalDate endAt,
                           List<Itinerary> itineraries) {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(name).append("\"\n");
        sb.append("기간 : ").append(startAt).append(" ~ ").append(endAt).append("\n");

        //그에 따라 수정 필요
        IntStream.range(0, itineraries.size())
            .forEach(index -> {
                sb.append("[").append(index + 1).append("번째 여정]\n");
                sb.append(itineraries.get(index).toString()).append("\n");
            });
        return sb.toString();
    }
}
