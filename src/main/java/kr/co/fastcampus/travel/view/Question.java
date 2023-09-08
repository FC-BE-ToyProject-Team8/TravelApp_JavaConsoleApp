package kr.co.fastcampus.travel.view;

import lombok.Getter;

@Getter
public class Question {
    private final String DEPARTURE_QUESTION = "출발지:";
    private final String DESTINATION_QUESTION = "도착지:";
    private final String DEPARTURE_AT_QUESTION = "출발 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):";
    private final String ARRIVE_AT_QUESTION = "도착 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):";
    private final String ACCOMMODATION_QUESTION = "숙박지명:";
    private final String CHECK_IN_QUESTION = "체크인 시간 (YYYY-mm-DD HH:MM 형식으로 입력):";
    private final String CHECK_OUT_QUESTION = "체크아웃 시간 (YYYY-mm-DD HH:MM 형식으로 입력):";

}
