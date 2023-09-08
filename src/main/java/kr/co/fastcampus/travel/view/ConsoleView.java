package kr.co.fastcampus.travel.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.controller.TravelController;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;

public class ConsoleView {
    private boolean isExited = false;

    private final TravelController travelController;
    private final InputView inputView;

    public ConsoleView() {
        travelController = new TravelController();
        inputView = new InputView();
    }

    public void process() {
        System.out.println("[메뉴]");
        System.out.println(
            Arrays.stream(Menu.values())
                .map(menu -> String.format("%d: %s", menu.getNumber(), menu.getName()))
                .collect(Collectors.joining(", "))
        );

        System.out.println("\n메뉴 번호를 입력해주세요");

        Menu menu = inputView.inputMenu();
        if (menu == Menu.LOG_TRIP) {
            logTrip();
        }

        isExited = true;
    }

    private void logTrip() {
        System.out.println("여행 기록을 시작합니다.\n");

        System.out.println("여행 이름:");
        final String name = inputView.inputNotEmptyString(
            str -> !str.contains(","),
            "컴마(,)는 입력할 수 없습니다."
        );

        System.out.println("시작 날짜 (2023-01-01 형식으로 입력):");
        LocalDate startAt = inputView.inputDate();

        System.out.println("종료 날짜 (2023-01-01 형식으로 입력):");
        LocalDate endAt = inputView.inputDate();

        List<ItinerarySaveRequest> itinerarySaveRequests = new ArrayList<>();
        int order = 1;

        String willContinueStr = "n";
        while ("n".equalsIgnoreCase(willContinueStr)) {
            ItinerarySaveRequest itinerarySaveRequest = logOneItinerary(order);
            itinerarySaveRequests.add(itinerarySaveRequest);

            System.out.println("\n여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.");
            willContinueStr = inputView.inputNotEmptyString();

            System.out.println();
            order++;
        }

        TripSaveRequest tripSaveRequest = TripSaveRequest.builder()
            .name(name)
            .startAt(startAt)
            .endAt(endAt)
            .itinerarySaveRequests(itinerarySaveRequests)
            .build();

        travelController.saveTrip(tripSaveRequest);
        System.out.println("여행 및 여정 기록이 완료되었습니다.");
    }

    private ItinerarySaveRequest logOneItinerary(int order) {
        System.out.printf("[%d번째 여정]\n", order);

        System.out.println("출발지:");
        String departure = inputView.inputNotEmptyString(
            str -> !str.contains(","),
            "컴마(,)는 입력할 수 없습니다."
        );

        System.out.println("도착지: ");
        String destination = inputView.inputNotEmptyString(
            str -> !str.contains(","),
            "컴마(,)는 입력할 수 없습니다."
        );

        ItinerarySaveRequest itinerarySaveRequest = null;
        while (itinerarySaveRequest == null) {
            System.out.println("출발 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):");
            LocalDateTime departureAt = inputView.inputDateTime();

            System.out.println("도착 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):");
            LocalDateTime arriveAt = inputView.inputDateTime();

            System.out.println("숙박지 (Enter로 생략 가능):");
            String accommodation = inputView.inputString(
                str -> !str.contains(","),
                "컴마(,)는 입력할 수 없습니다."
            );

            System.out.println("체크인 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):");
            LocalDateTime checkInAt = inputView.inputDateTime();

            System.out.println("체크아웃 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):");
            LocalDateTime checkOutAt = inputView.inputDateTime();

            try {
                itinerarySaveRequest = ItinerarySaveRequest.builder()
                    .departure(departure)
                    .destination(destination)
                    .departureAt(departureAt)
                    .arriveAt(arriveAt)
                    .accommodation(accommodation)
                    .checkInAt(checkInAt)
                    .checkOutAt(checkOutAt)
                    .build();
            } catch (IllegalArgumentException e) {
                System.out.println("'출발과 도착' 또는 '체크인과 체크아웃' 중 하나의 시간은 꼭 입력하셔야 합니다.");
            }
        }

        return itinerarySaveRequest;
    }

    public boolean isExited() {
        return isExited;
    }
}
