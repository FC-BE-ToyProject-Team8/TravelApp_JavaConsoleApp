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
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.common.exception.UnknownException;
import kr.co.fastcampus.travel.controller.TravelController;
import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.domain.FileType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleView {

    private final TravelController travelController;

    private final BufferedReader br;
    private boolean isExited = false;

    private final TravelController travelController = new TravelController();

    public ConsoleView() {
        br = new BufferedReader(new InputStreamReader(System.in));
        travelController = new TravelController();
    }

    public void process() {
        System.out.println("[메뉴]");
        System.out.println(Arrays.stream(Menu.values())
                .map(menu -> String.format("%d: %s", menu.getNumber(), menu.getName()))
                .collect(Collectors.joining(", ")));

        System.out.println("\n메뉴 번호를 입력해주세요");

        Menu menu = inputMenu();
        if (menu == Menu.LOG_TRIP) {
            logTrip();
            Long id = 1L;
            logItineraries(id);
        } else if (menu == Menu.LOG_ITINERARY) {
            // 창호님의 여행 전체 조회 메소드 호출
            // 창호님의 여행 1개 보는 메소드 호출
            Long id = 1L;
            logItineraries(id);
        } else if (menu == Menu.SHOW_ITINERARY) {
            showItinerary();
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
        Question question = new Question();
        while (!isDone) {
            String departure = isValidAnswer(question.getDepartureQuestion());
            String destination = isValidAnswer(question.getDestinationQuestion());
            String departureAt = isValidLocalDateTime(question.getDepartureAtQuestion());
            String arriveAt = isValidLocalDateTime(question.getArriveAtQuestion());
            String accommodation = isValidAnswer(question.getAccommodationQuestion());
            String checkInAt = isValidCheckInAndCheckOutTime(question.getCheckInAtQuestion());
            String checkOutAt = isValidCheckInAndCheckOutTime(question.getCheckOutAtQuestion());

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

    private void showItinerary() {
        List<TripInfoResponse> trips = travelController.getTripList();
        if (trips.isEmpty()) {
            System.out.println("여행 목록이 비어 있습니다.");
            throw new TravelDoesNotExistException();
        }
        System.out.println("여정 조회를 시작합니다.");
        for (TripInfoResponse tripInfo : trips) {
            System.out.println(String.format("%d: %s (%s ~ %s)",
                    tripInfo.id(), tripInfo.name(), tripInfo.startAt(), tripInfo.endAt()));
        }
        System.out.println("조회할 여행의 번호를 입력하세요.");
        Long tripNum = (long) inputNumber("잘못된 여행 번호입니다. 다시 입력해주세요");
        System.out.print("조회할 여행의 데이터 타입을 입력하세요. (1. CSV, 2. JSON) : ");
        FileType fileType = inputFileType();
        List<ItineraryInfoResponse> itineraries = travelController.getItineraryList(fileType,
                tripNum);
        System.out.println("여정 목록");
        for (ItineraryInfoResponse itineraryInfo : itineraries) {
            System.out.println(
                    String.format("%d: %s ~ %s",
                            itineraryInfo.id(), itineraryInfo.departure(), itineraryInfo.destination()));
        }
        System.out.print("조회할 여정의 번호를 입력해주세요. : ");
        Long itineraryNum = (long) inputNumber("잘못된 여정 번호입니다. 다시 입력해주세요");
        System.out.print("조회할 여정의 데이터 타입을 입력하세요. (1. CSV, 2. JSON) : ");
        fileType = inputFileType();
        ItineraryResponse itineraryResponse = travelController.findItinerary(fileType,
                itineraryNum);
        printItineraryDetail(itineraryResponse);
    }

    private void printItineraryDetail(ItineraryResponse itineraryResponse) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("출발 : ").append(itineraryResponse.departure()).append(", ")
                .append(formatterDate.format(itineraryResponse.departureAt())).append("\n");
        sb.append("도착 : ").append(itineraryResponse.destination())
                .append(formatterDate.format(itineraryResponse.departureAt())).append("\n");
        sb.append("숙박 시설: ").append(itineraryResponse.accommodation()).append("\n");
        sb.append("체크인: ").append(formatterTime.format(itineraryResponse.checkInAt())).append("\n");
        sb.append("체크아웃: ").append(formatterTime.format(itineraryResponse.checkOutAt()))
                .append("\n");
        System.out.println(sb);
    }

    private FileType inputFileType() {
        return null;
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

    public static LocalDateTime stringToLocalDateTime(String dateString) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, outputFormatter);
        return localDateTime;
    }

    private String isValidLocalDateTime(String input) {
        String answer;
        while (true) {
            System.out.println(input);
            answer = readLine();
            if (answer.isEmpty()) {
                break;
            } else {
                if (!containsComma(answer)) {
                    if (isValidDate(answer)) {
                        break;
                    }
                }
                System.out.println("0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요");
            }
        }
        return answer;
    }

    private String isValidCheckInAndCheckOutTime(String input) {
        String answer;
        while (true) {
            System.out.println(input);
            answer = readLine();
            if (!containsComma(answer)) {
                if (isValidDate(answer)) {
                    break;
                }
            }
            System.out.println("0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요");
        }
        return answer;
    }

    private String isValidAnswer(String input) {
        String answer;
        while (true) {
            System.out.println(input);
            answer = readLine();
            if (!isEmpty(answer)) {
                break;
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

    private ItinerarySaveRequest setItinerarySaveRequest(
            String departure,
            String destination,
            String departureAt,
            String arriveAt,
            String accommodation,
            String checkInAt,
            String checkOutAt
    ) {
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

