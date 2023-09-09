package kr.co.fastcampus.travel.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
            logTrips();
        } else if (menu == Menu.LOG_ITINERARY) {
            logItineraries();
        } else if (menu == Menu.SHOW_TRIP) {
            showTrip();
        } else if (menu == Menu.SHOW_ITINERARY) {
            showItinerary();
        } else if (menu == Menu.EXIT) {
            System.out.println("프로그램을 종료합니다.");
            isExited = true;
        }
    }

    private void logTrips() {
        int order = 1;
        String willContinueStr = "y";
        while ("y".equalsIgnoreCase(willContinueStr)) {
            System.out.printf("[%d번째 여행]\n", order);
            logTrip();

            System.out.println("\n여행 기록을 계속하시겠습니까? (y/n)");
            willContinueStr = inputView.inputNotEmptyString();

            System.out.println();
            order++;
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
        final LocalDate startAt = inputView.inputDate();

        System.out.print("종료 ");
        final LocalDate endAt = inputView.inputDate();

        List<ItinerarySaveRequest> itinerarySaveRequests = new ArrayList<>();
        int order = 1;

        System.out.println("\n여행에 대한 여정 기록을 시작합니다.\n");

        String willContinueStr = "y";
        while ("y".equalsIgnoreCase(willContinueStr)) {
            ItinerarySaveRequest itinerarySaveRequest = logOneItinerary(order);
            itinerarySaveRequests.add(itinerarySaveRequest);

            System.out.println("\n여정 기록을 계속하시겠습니까? (Y/N)");
            willContinueStr = inputView.inputNotEmptyString(
                input -> input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"),
                "Y(y) 또는 N(n) 중 하나를 입력해주세요.");
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

    private void logItineraries() {
        Long tripId = getTripId();
        System.out.println("\n여행에 대한 여정 기록을 시작합니다.\n");

        List<ItinerarySaveRequest> itinerarySaveRequests = new ArrayList<>();
        int order = 1;
        String willContinueStr = "y";
        while ("y".equalsIgnoreCase(willContinueStr)) {
            ItinerarySaveRequest itinerarySaveRequest = logOneItinerary(order);
            itinerarySaveRequests.add(itinerarySaveRequest);

            System.out.println("\n여정 기록을 계속하시겠습니까? (Y/N)");
            willContinueStr = inputView.inputNotEmptyString(
                input -> input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"),
                "Y(y) 또는 N(n) 중 하나를 입력해주세요.");
            System.out.println();
            order++;
        }

        travelController.saveItineraries(tripId, itinerarySaveRequests);
        System.out.println("여행 및 여정 기록이 완료되었습니다.");
    }

    private List<TripInfoResponse> getTripList(FileType fileType) {
        List<TripInfoResponse> tripInfoResponses;
        try {
            tripInfoResponses = travelController.getTripList(fileType);
        } catch (TravelDoesNotExistException e) {
            System.out.println("\n등록된 여행이 없습니다. 여행을 먼저 등록해주세요.");
            return null;
        }
        return tripInfoResponses;
    }

    private Long getTripId() {
        return getTripId(FileType.CSV);
    }

    private Long getTripId(FileType fileType) {
        List<TripInfoResponse> tripInfoResponses = getTripList(fileType);
        if (tripInfoResponses == null) {
            return null;
        }
        printShortTripInfo(tripInfoResponses);
        return inputTripNumber(tripInfoResponses);
    }


    private void showTrip() {
        System.out.print("조회할 파일 형식을 선택해주세요. (1. CSV / 2. JSON)");
        FileType fileType = inputView.inputFileType();
        Long travelId = getTripId(fileType);
        if (travelId == null) {
            return;
        }
        TripResponse tripResponse = travelController.findTrip(fileType, travelId);
        printDetailTripInfo(tripResponse);
    }

    private void printShortTripInfo(List<TripInfoResponse> tripInfoResponses) {
        for (TripInfoResponse tripInfoResponse : tripInfoResponses) {
            System.out.printf("%d: %s (%s ~ %s)%n",
                tripInfoResponse.id(),
                tripInfoResponse.name(),
                tripInfoResponse.startAt(),
                tripInfoResponse.endAt());
        }
    }

    private void printDetailTripInfo(TripResponse tripResponse) {
        System.out.printf("\"%s\"%n기간: %s ~ %s%n%n",
            tripResponse.name(),
            tripResponse.startAt(),
            tripResponse.endAt());
        int cnt = 1;
        List<ItineraryResponse> itineraryResponses = tripResponse.itineraries();
        for (ItineraryResponse itineraryResponse : itineraryResponses) {
            System.out.printf("[%d번째 여정]%n", cnt);
            printItineraryDetail(itineraryResponse);
            cnt++;
        }
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
            System.out.print("조회할 파일 형식을 선택해주세요. (1. CSV / 2. JSON)");
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
            System.out.println("등록된 여행이 없습니다. 여행을 먼저 등록해주세요.");
        }
    }

    private Long inputItineraryNumber(List<ItineraryInfoResponse> itineraries) {
        System.out.println("조회할 여정의 번호를 입력해주세요.");
        return (long) inputView.inputNumber("잘못된 여정 번호입니다. 다시 입력해주세요",
            num -> num >= 1 && num <= itineraries.size());
    }

    private Long inputTripNumber(List<TripInfoResponse> trips) {
        System.out.println("조회할 여행의 번호를 입력해주세요.");
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
        final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        appendField(sb, "출발", itineraryResponse.departure());
        appendField(sb, "도착", itineraryResponse.destination());
        appendDateField(sb, "출발일", itineraryResponse.departureAt(), formatterDate);
        appendDateField(sb, "도착일", itineraryResponse.arriveAt(), formatterDate);
        appendField(sb,
            "숙박 시설",
            itineraryResponse.accommodation().isEmpty() ? "X" : itineraryResponse.accommodation()
        );
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

    private void appendDateField(
        StringBuilder sb,
        String label,
        LocalDateTime value,
        DateTimeFormatter formatter
    ) {
        sb.append(label).append(" : ");
        if (value != null) {
            sb.append(formatter.format(value));
        } else {
            sb.append("X");
        }
        sb.append('\n');
    }

    public boolean isExited() {
        return isExited;
    }
}

