package kr.co.fastcampus.travel.view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.common.exception.TravelDoesNotExistException;
import kr.co.fastcampus.travel.common.exception.UnknownException;
import java.util.List;
import kr.co.fastcampus.travel.controller.TravelController;
import kr.co.fastcampus.travel.controller.dto.ItineraryInfoResponse;
import kr.co.fastcampus.travel.controller.dto.ItineraryResponse;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.domain.FileType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleView {

    private final TravelController travelController;

    private final BufferedReader br;
    private boolean isExited = false;

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
        } else if (menu == Menu.SHOW_ITINERARY) {
            showItinerary();
        }

        isExited = true;
    }

    private void logTrip() {
        System.out.println("여행 기록을 시작합니다.");

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

    public boolean isExited() {
        return isExited;
    }
}

