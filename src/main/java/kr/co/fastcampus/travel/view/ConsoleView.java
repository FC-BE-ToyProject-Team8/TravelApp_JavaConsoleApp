package kr.co.fastcampus.travel.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import kr.co.fastcampus.travel.AppConfig;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.controller.TravelController;
import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.ItinerarySaveRequest;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.controller.dto.TripResponse;
import kr.co.fastcampus.travel.controller.dto.TripSaveRequest;
import kr.co.fastcampus.travel.view.enums.FileType;
import kr.co.fastcampus.travel.view.enums.Menu;

public class ConsoleView {

    private boolean isExited = false;

    private final TravelController travelController;
    private final InputView inputView;

    public ConsoleView() {
        travelController = AppConfig.travelController();
        inputView = new InputView();
    }


    public void process() {
        Menu menu = inputView.inputMenu();
        if (menu == Menu.LOG_TRIP) {
            logTrip();
        } else if (menu == Menu.LOG_ITINERARY) {
            FileType fileType = FileType.fromNumber(1);
            Long tripId = getTripId(fileType);
            if (tripId == null) {
                return;
            }
            logItineraries(tripId);
        } else if (menu == Menu.SHOW_TRIP) {
            showTrip();
        } else if (menu == Menu.SHOW_ITINERARY) {
            showItinerary();
        } else if (menu == Menu.EXIT) {
            isExited = true;
        }

    }

    private void logTrip() {
        System.out.println("여행 기록을 시작합니다.\n");

        System.out.println("여행 이름:");
        final String name = inputView.inputNotEmptyString(
            str -> !str.contains(","),
            "컴마(,)는 입력할 수 없습니다."
        );

        System.out.print("시작 ");
        LocalDate startAt = inputView.inputDate();

        System.out.print("종료 ");
        LocalDate endAt = inputView.inputDate();

        List<ItinerarySaveRequest> itinerarySaveRequests = new ArrayList<>();
        int order = 1;

        System.out.println("\n여행에 대한 여정 기록을 시작합니다.\n");

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

    private void logItineraries(Long tripId) {
        System.out.println("\n여행에 대한 여정 기록을 시작합니다.\n");

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

        travelController.saveItineraries(tripId, itinerarySaveRequests);
        System.out.println("여행 및 여정 기록이 완료되었습니다.");
    }

    private List<TripInfoResponse> getTripList(FileType fileType){
        List<TripInfoResponse> tripInfoResponses;
        try {
            tripInfoResponses = travelController.getTripList(fileType);
        } catch (TravelDoesNotExistException e) {
            System.out.println("\n등록된 여행이 없습니다. 여행을 먼저 등록해주세요.");
            return null;
        }
        return tripInfoResponses;
    }
    private Long getTripId(FileType fileType){
        List<TripInfoResponse> tripInfoResponses = getTripList(fileType);
        if (tripInfoResponses == null) {
            return null;
        }
        printShortTripInfo(tripInfoResponses);
        Long travelId = inputTripNumber(tripInfoResponses);
        return travelId;
    }


    //예외 처리 과정 피드백 필요!
    //"잘못된 여행 번호 입니디." 라는 문구 보단 해당 번호로 controller를 통해 조회 후
    // 없다는 걸 인지 그리고 예외를 처리하는 것이기 때문에 이름도 바꿔야 하지 않나 싶습니다.
    private void showTrip() {
        System.out.print("조회 타입의 번호를 입력해주세요. (1.CSV/2.JSON) ");
        FileType fileType = inputView.inputFileType();
        Long travelId = getTripId(fileType);
        if (travelId == null) {
            return;
        }
        TripResponse tripResponse = travelController.findTrip(fileType, travelId);
        System.out.println(printDetailTripInfo(tripResponse));
    }

    private void printShortTripInfo(List<TripInfoResponse> tripInfoResponses) {
        for (TripInfoResponse tripInfoResponse : tripInfoResponses){
            System.out.printf("%d: %s (%s ~ %s)%n",
                    tripInfoResponse.id(),
                    tripInfoResponse.name(),
                    tripInfoResponse.startAt(),
                    tripInfoResponse.endAt());
        }
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
        System.out.printf("[추가할 %d번째 여정]\n", order);

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
            System.out.print("출발 ");
            LocalDateTime departureAt = inputView.inputDateTime();

            System.out.print("도착 ");
            LocalDateTime arriveAt = inputView.inputDateTime();

            System.out.println("숙박지 (Enter로 생략 가능):");
            String accommodation = inputView.inputString(
                str -> !str.contains(","),
                "컴마(,)는 입력할 수 없습니다."
            );

            System.out.print("체크인 ");
            LocalDateTime checkInAt = inputView.inputDateTime();

            System.out.print("체크아웃 ");
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
        try {
            System.out.println("여정 조회를 시작합니다.");
            System.out.print("조회할 여행의 데이터 타입을 입력하세요. (1. CSV, 2. JSON) : ");
            FileType fileType = inputView.inputFileType();
            List<TripInfoResponse> trips = travelController.getTripList(fileType);
            printTripList(trips);
            Long tripNum = inputTripNumber(trips);
            List<ItineraryInfoResponse> itineraries = travelController.getItineraryList(fileType,
                    tripNum);
            printItineraryList(itineraries);
            Long itineraryNum = inputItineraryNumber(itineraries);
            Long itineraryIndex = itineraries.get((int) (itineraryNum - 1)).id();
            ItineraryResponse itineraryResponse = travelController.findItinerary(fileType,
                    itineraryIndex);
            printItineraryDetail(itineraryResponse);
        } catch (TravelDoesNotExistException e) {
            System.out.println("조회할 수 있는 여행이 없습니다.");
        }
    }

    private Long inputItineraryNumber(List<ItineraryInfoResponse> itineraries) {
        System.out.print("조회할 여정의 번호를 입력해주세요. : ");
        return (long) inputView.inputNumber("잘못된 여정 번호입니다. 다시 입력해주세요",
                num -> num >= 1 && num <= itineraries.size());
    }

    private Long inputTripNumber(List<TripInfoResponse> trips) {
        System.out.print("조회할 여행의 번호를 입력해주세요. : ");
        return (long) inputView.inputNumber("잘못된 여행 번호입니다. 다시 입력해주세요",
                num -> num >= 1 && num <= trips.size());
    }

    private void printItineraryList(List<ItineraryInfoResponse> itineraries) {
        System.out.println("여정 목록");
        for (int i = 0; i < itineraries.size(); i++) {
            System.out.printf("%d: %s ~ %s%n", i + 1,
                    itineraries.get(i).departure(), itineraries.get(i).destination());
        }
    }

    private void printTripList(List<TripInfoResponse> trips) {
        System.out.println("여행 목록");
        for (TripInfoResponse tripInfo : trips) {
            System.out.printf("%d: %s (%s ~ %s)%n",
                    tripInfo.id(), tripInfo.name(), tripInfo.startAt(), tripInfo.endAt());
        }
    }

    private void printItineraryDetail(ItineraryResponse itineraryResponse) {
        System.out.println("여정 정보");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        appendField(sb, "출발", itineraryResponse.departure());
        appendField(sb, "도착", itineraryResponse.destination());
        appendDateField(sb, "출발일", itineraryResponse.departureAt(), formatterDate);
        appendDateField(sb, "도착일", itineraryResponse.arriveAt(), formatterDate);
        appendField(sb, "숙박 시설", itineraryResponse.accommodation().isEmpty() ? "X" : itineraryResponse.accommodation());
        appendDateField(sb, "체크인", itineraryResponse.checkInAt(), formatterTime);
        appendDateField(sb, "체크아웃", itineraryResponse.checkOutAt(), formatterTime);
        System.out.println(sb);
    }

    private void appendField(StringBuilder sb, String label, String value) {
        sb.append(label).append(" : ");
        if (value != null) {
            sb.append(value);
        } else {
            sb.append("X");
        }
        sb.append('\n');
    }

    private void appendDateField(StringBuilder sb, String label, LocalDateTime value, DateTimeFormatter formatter) {
        sb.append(label).append(" : ");
        if (value != null) {
            sb.append(formatter.format(value));
        } else {
            sb.append("X");
        }
        sb.append('\n');
    }

    private static LocalDate stringToLocalDate(String dateString) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, outputFormatter);
    }

    public static LocalDateTime stringToLocalDateTime(String dateString) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateString, outputFormatter);
    }

    public boolean isExited() {
        return isExited;
    }
}

