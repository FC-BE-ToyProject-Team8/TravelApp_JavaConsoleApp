package kr.co.fastcampus.travel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            Long id = 0L;// 진홍님의 여행 기록 저장 후 넘어온 여행의 id 값 패싱
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
            String departureQuestion = "출발지:";
            String departure = isValidAnswer(departureQuestion);
            String destinationQuestion = "도착지:";
            String destination = isValidAnswer(destinationQuestion);
            String departureAtQuestion = "출발 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):";
            String departureAt = isValidDepartureAndDestinationTime(departureAtQuestion);
            String arriveAtQuestion = "도착 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):";
            String arriveAt = isValidDepartureAndDestinationTime(arriveAtQuestion);
            String accommodationQuestion = "숙박지명:";
            String accommodation = isValidAnswer(accommodationQuestion);
            String checkInAtQuestion = "체크인 시간 (YYYY-mm-DD HH:MM 형식으로 입력):";
            String checkInAt = isValidCheckInAndCheckOutTime(checkInAtQuestion);
            String checkOutAtQuestion = "체크아웃 시간 (YYYY-mm-DD HH:MM 형식으로 입력):";
            String checkOutAt = isValidCheckInAndCheckOutTime(checkOutAtQuestion);

            ItinerarySaveRequest itineraryRequest = setItinerarySaveRequest(
                    departure,
                    destination,
                    departureAt,
                    arriveAt,
                    accommodation,
                    checkInAt,
                    checkOutAt
            );
            saveRequests.add(itineraryRequest);
            System.out.println("여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.");
            String choice = readLine();
            if (choice.equals("Y") || choice.equals("y")) {
                isDone = true;
            }
        }
        travelController.saveItineraries(id, saveRequests);
        System.out.println("여정 기록이 완료되었습니다.");
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

    private String isValidDepartureAndDestinationTime(String input) {
        boolean check = false;
        String answer = "";
        while (!check) {
            System.out.println(input);
            answer = readLine();
            if (answer.isEmpty()) {
                check = true;
            } else {
                if (!containsComma(answer)) {
                    if (isValidDate(answer)) {
                        check = true;
                    }
                }
                System.out.println("0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요");
            }
        }
        return answer;
    }

    private String isValidCheckInAndCheckOutTime(String input) {
        boolean check = false;
        String answer = "";
        while (!check) {
            System.out.println(input);
            answer = readLine();
            if (!containsComma(answer)) {
                if (isValidDate(answer)) {
                    check = true;
                }
            }
            System.out.println("0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요");
        }
        return answer;
    }

    private String isValidAnswer(String input) {
        boolean check = false;
        String answer = "";
        while (!check) {
            System.out.println(input);
            answer = readLine();
            if (!isEmpty(answer)) {
                check = true;
            }
        }
        return answer;
    }

    private boolean isEmpty(String input) {
        if (input.trim().isEmpty()) {
            System.out.println("빈 칸으로는 입력할 수 없습니다. 다시 입력해주세요.");
            return true;
        }
        return false;
    }

    private boolean containsComma(String input) {
        if (input.contains(",")) {
            System.out.println("컴마(,)는 입력할 수 없습니다.");
            return true;
        }
        return false;
    }

    private boolean isValidDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private ItinerarySaveRequest setItinerarySaveRequest(String departure,
                                                         String destination,
                                                         String departureAt,
                                                         String arriveAt,
                                                         String accommodation,
                                                         String checkInAt,
                                                         String checkOutAt) {
        return new ItinerarySaveRequest(
                departure,
                destination,
                stringToLocalDateTime(departureAt),
                stringToLocalDateTime(arriveAt),
                accommodation,
                stringToLocalDateTime(checkInAt),
                stringToLocalDateTime(checkOutAt)
        );
    }


    public boolean isExited() {
        return isExited;
    }
}
