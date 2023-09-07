package kr.co.fastcampus.travel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.common.exception.UnknownException;

public class ConsoleView {

    private boolean isExited = false;

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
