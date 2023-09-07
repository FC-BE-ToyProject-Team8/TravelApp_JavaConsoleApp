package kr.co.fastcampus.travel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;
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
            System.out.println(tripInfoResponse);
        }

        System.out.println("조회할 여행의 번호를 입력해주세요.");
        Long travelId = (long) inputNumber("잘못된 여행 번호입니다. 다시 입력해주세요");
        TripResponse trip = travelController.findTrip(fileType, travelId);

        System.out.println(trip);

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
        int fileNum = inputNumber("잘못된 번호입니다. 다시 입력해주세요");

        FileType fileType;
        if (fileNum == FileType.CSV.getNumber()) {
            fileType = FileType.CSV;
        } else {
            fileType = FileType.JSON;
        }
        return fileType;
    }

    public boolean isExited() {
        return isExited;
    }
}
