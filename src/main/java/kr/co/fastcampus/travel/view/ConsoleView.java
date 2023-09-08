package kr.co.fastcampus.travel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kr.co.fastcampus.travel.common.exception.UnknownException;
import kr.co.fastcampus.travel.controller.TravelController;
import kr.co.fastcampus.travel.controller.dto.TripInfoResponse;
import kr.co.fastcampus.travel.controller.dto.TripResponse;
import kr.co.fastcampus.travel.domain.FileType;

public class ConsoleView {

    private boolean isExited = false;
    private TravelController travelController;

    private final BufferedReader br;

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
        } else if (menu == Menu.SHOW_TRIP) {
            showTrip();
        }

        isExited = true;
    }

    private void logTrip() {
        System.out.println("여행 기록을 시작합니다.");

    }

    private void showTrip() {
        FileType fileType = inputFileType();

        System.out.print("조회 타입의 번호를 입력해주세요. (1.CSV/2.JSON) ");
        List<TripInfoResponse> tripInfoResponses = travelController.getTripList(fileType);
        for (TripInfoResponse tripInfoResponse : tripInfoResponses) {
            System.out.println(printShortTripInfo(tripInfoResponse));
        }

        System.out.println("조회할 여행의 번호를 입력해주세요.");
        Long travelId = (long) inputNumber("잘못된 여행 번호입니다. 다시 입력해주세요");
        TripResponse tripResponse = travelController.findTrip(fileType, travelId);
        System.out.println(printDetailTripInfo(tripResponse));
    }

    private String printShortTripInfo(TripInfoResponse tripInfoResponse) {
        return String.format("%d : %s + ( %s ~ %s )",
            tripInfoResponse.id(),
            tripInfoResponse.name(),
            tripInfoResponse.startAt(),
            tripInfoResponse.endAt());
    }

    //리팩토링 예정
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

    private FileType inputFileType() {
        try {
            int fileNum = inputNumber("잘못된 번호입니다. 다시 입력해주세요");
            return FileType.fromNumber(fileNum);
        } catch (IllegalArgumentException e) {
            return inputFileType();
        }
    }

    private static LocalDate stringToLocalDate(String dateString) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, outputFormatter);
        return localDate;
    }

    public boolean isExited() {
        return isExited;
    }
}
