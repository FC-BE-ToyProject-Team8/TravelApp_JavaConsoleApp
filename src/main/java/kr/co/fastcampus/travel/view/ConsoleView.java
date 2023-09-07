package kr.co.fastcampus.travel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kr.co.fastcampus.travel.common.exception.UnknownException;
import kr.co.fastcampus.travel.controller.TravelController;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;

public class ConsoleView {
    private boolean isExited = false;

    private final BufferedReader br;

    private final TravelController travelController = new TravelController();

    public ConsoleView() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void process() {
        System.out.println("[메뉴]");
        System.out.println(
                Arrays.stream(Menu.values())
                        .map(menu -> String.format("%d: %s", menu.getNumber(), menu.getName()))
                        .collect(Collectors.joining(", "))
        );

        System.out.println("\n메뉴 번호를 입력해주세요");

        Menu menu = inputMenu();
        if (menu == Menu.LOG_TRIP) {
            logTrip();
            Long id = 0L;// 여행 기록 저장 후 넘어온 여행의 id 값 패싱
            logItineraries(id);
        } else if (menu == Menu.LOG_ITINERARY) {
            // 창호님의 여행 전체 조회 메소드 호출
            // 창호님의 여행 1개 보는 메소드 호출
            Long id = 0L;// 1개의 여행의 id 값 패싱
            logItineraries(id);
        }

        isExited = true;
    }

    private void logTrip() {
        System.out.println("여행 기록을 시작합니다.");

    }

    private void logItineraries(Long id) {
        System.out.println("여행에 대한 여정 기록을 시작합니다.");
        boolean isDone = false;
        List<ItinerarySaveRequest> saveRequests = new ArrayList<>();
        while (!isDone) {
            System.out.println("출발지:");
            String departure = readLine();
            System.out.println("도착지:");
            String destination = readLine();
            System.out.println("출발 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):");
            String departureAt = readLine();
            System.out.println("도착 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):");
            String arriveAt = readLine();
            System.out.println("숙박지명:");
            String accommodation = readLine();
            System.out.println("체크인 시간 (YYYY-mm-DD HH:MM 형식으로 입력):");
            String checkInAt = readLine();
            System.out.println("체크아웃 시간 (YYYY-mm-DD HH:MM 형식으로 입력):");
            String checkOutAt = readLine();
            ItinerarySaveRequest itineraryRequest = new ItinerarySaveRequest(
                    departure,
                    destination,
                    stringToLocalDateTime(departureAt),
                    stringToLocalDateTime(arriveAt),
                    accommodation,
                    stringToLocalDateTime(checkInAt),
                    stringToLocalDateTime(checkOutAt)
            );
            saveRequests.add(itineraryRequest);
            System.out.println("여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.");
            if (readLine().equals("Y") || readLine().equals("y")) {
                isDone = true;
            }
        }
        travelController.saveItineraries(id, saveRequests);
    }

    private Menu inputMenu() {
        try {
            int menuNumber = inputNumber("잘못된 메뉴 번호입니다. 다시 입력해주세요;");
            return Menu.fromNumber(menuNumber);
        } catch (IllegalArgumentException e) {
            return inputMenu();
        }
    }

    private int inputNumber(String errorMessage) {
        while (true) {
            try {
                return parseInt(readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(errorMessage);
            }
        }
    }

    private int parseInt(String strNum) {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private String readLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("입력을 받는 도중 알 수 없는 에러가 발생했습니다.");
            throw new UnknownException();
        }
    }

    private LocalDateTime stringToLocalDateTime(String dateString) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, outputFormatter);
        return localDateTime;
    }

    public boolean isExited() {
        return isExited;
    }
}
