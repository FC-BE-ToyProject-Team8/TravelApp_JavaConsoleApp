package kr.co.fastcampus.travel.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.TravelController;
import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.controller.dto.TripResponse;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.domain.FileType;

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
        System.out.println(Arrays.stream(Menu.values())
                .map(menu -> String.format("%d: %s", menu.getNumber(), menu.getName()))
                .collect(Collectors.joining(", ")));

        System.out.println("\n메뉴 번호를 입력해주세요");

        Menu menu = inputView.inputMenu();
        if (menu == Menu.LOG_TRIP) {
            logTrip();
        } else if (menu == Menu.SHOW_TRIP) {
            showTrip();
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
            String choice = inputView.readLine();
            if (choice.equals("Y") || choice.equals("y")) {
                isDone = true;
            }
        }
        travelController.saveItineraries(id, saveRequests);
        System.out.println("여정 기록이 완료되었습니다.");
    }

    //예외 처리 과정 피드백 필요!
    //"잘못된 여행 번호 입니디." 라는 문구 보단 해당 번호로 controller를 통해 조회 후
    // 없다는 걸 인지 그리고 예외를 처리하는 것이기 때문에 이름도 바꿔야 하지 않나 싶습니다.
    private void showTrip() {
        System.out.print("조회 타입의 번호를 입력해주세요. (1.CSV/2.JSON) ");
        FileType fileType = inputFileType();

        try {
            System.out.println("\n조회할 여행의 번호를 입력해주세요.");
            Long travelId = (long) inputNumber("ID(숫자)를 입력해 주세요.");
            TripResponse tripResponse = travelController.findTrip(fileType, travelId);
            System.out.println(printDetailTripInfo(tripResponse));
        } catch (TravelDoesNotExistException e) {
            System.out.println("\n 잘못된 여행 번호입니다. 다시 입력햊주세요");
        }

        List<TripInfoResponse> tripInfoResponses;
        try {
            tripInfoResponses = travelController.getTripList(fileType);
        } catch (TravelDoesNotExistException e) {
            System.out.println("\n등록된 여행이 없습니다.");
            return;
        }

        for (TripInfoResponse tripInfoResponse : tripInfoResponses) {
            System.out.println(printShortTripInfo(tripInfoResponse));
        }

        System.out.println("\n조회할 여행의 번호를 입력해주세요.");
        Long travelId = (long) inputView.inputNumber(
            "잘못된 여행 번호입니다. 다시 입력해주세요",
            tripNum -> tripInfoResponses.stream()
                .anyMatch(it -> Objects.equals(it.id(), Long.valueOf(tripNum)))
        );

        TripResponse tripResponse = travelController.findTrip(fileType, travelId);
        System.out.println(printDetailTripInfo(tripResponse));

    }

    private String printShortTripInfo(TripInfoResponse tripInfoResponse) {
        return String.format("%d : %s ( %s ~ %s ) ",
            tripInfoResponse.id(),
            tripInfoResponse.name(),
            tripInfoResponse.startAt(),
            tripInfoResponse.endAt());
    }

    //리팩토링 (토요일) 예정
    private String printDetailTripInfo(TripResponse tripResponse) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(tripResponse.name()).append("\"\n");
        sb.append("기간 : ").append(stringToLocalDate(tripResponse.startAt().toString()))
            .append(" ~ ").append(stringToLocalDate(tripResponse.endAt().toString())).append("\n");

        IntStream.range(0, tripResponse.itineraries().size())
            .forEach(index -> {
                sb.append("[").append(index + 1).append("번째 여정]\n");
                sb.append(tripResponse.itineraries().get(index).toString()).append("\n");
            });

        return sb.toString();
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

    private void showItinerary() {
        List<TripInfoResponse> trips = travelController.getTripList(FileType.CSV);
        if (trips.isEmpty()) {
            System.out.println("여행 목록이 비어 있습니다.");
            throw new TravelDoesNotExistException();
        }
        System.out.println("여정 조회를 시작합니다.");
        for (TripInfoResponse tripInfo : trips) {
            System.out.printf("%d: %s (%s ~ %s)%n",
                    tripInfo.id(), tripInfo.name(), tripInfo.startAt(), tripInfo.endAt());
        }
        System.out.println("조회할 여행의 번호를 입력하세요.");
        Long tripNum = (long) inputView.inputNumber("잘못된 여행 번호입니다. 다시 입력해주세요");
        System.out.print("조회할 여행의 데이터 타입을 입력하세요. (1. CSV, 2. JSON) : ");
        FileType fileType = inputFileType();
        List<ItineraryInfoResponse> itineraries = travelController.getItineraryList(fileType,
                tripNum);
        System.out.println("여정 목록");
        for (ItineraryInfoResponse itineraryInfo : itineraries) {
            System.out.printf("%d: %s ~ %s%n",
                    itineraryInfo.id(), itineraryInfo.departure(), itineraryInfo.destination());
        }
        System.out.print("조회할 여정의 번호를 입력해주세요. : ");
        Long itineraryNum = (long) inputView.inputNumber("잘못된 여정 번호입니다. 다시 입력해주세요");
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
        sb.append("도착 : ").append(itineraryResponse.destination()).append(", ")
                .append(formatterDate.format(itineraryResponse.departureAt())).append("\n");
        sb.append("숙박 시설: ").append(itineraryResponse.accommodation()).append("\n");
        sb.append("체크인: ").append(formatterTime.format(itineraryResponse.checkInAt())).append("\n");
        sb.append("체크아웃: ").append(formatterTime.format(itineraryResponse.checkOutAt()))
                .append("\n");
        System.out.println(sb);
    }

    private FileType inputFileType() {
        try {
            int fileNum = inputView.inputNumber("잘못된 번호입니다. 다시 입력해주세요");
            return FileType.fromNumber(fileNum);
        } catch (IllegalArgumentException e) {
            return inputFileType();
        }
    }

    private static LocalDate stringToLocalDate(String dateString) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, outputFormatter);
    }

    public static LocalDateTime stringToLocalDateTime(String dateString) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateString, outputFormatter);
    }

    private String isValidLocalDateTime(String input) {
        String answer;
        while (true) {
            System.out.println(input);
            answer = inputView.readLine();
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
            answer = inputView.readLine();
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
            answer = inputView.readLine();
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

