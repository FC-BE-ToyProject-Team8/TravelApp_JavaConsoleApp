package kr.co.fastcampus.travel.view;

import lombok.Getter;

@Getter
public class Question {
    private final String departureQuestion = "출발지:";
    private final String destinationQuestion = "도착지:";
    private final String departureAtQuestion = "출발 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):";
    private final String arriveAtQuestion = "도착 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):";
    private final String accommodationQuestion = "숙박지명:";
    private final String checkInAtQuestion = "체크인 시간 (YYYY-mm-DD HH:MM 형식으로 입력):";
    private final String checkOutAtQuestion = "체크아웃 시간 (YYYY-mm-DD HH:MM 형식으로 입력):";

}
