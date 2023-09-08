package kr.co.fastcampus.travel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Predicate;
import kr.co.fastcampus.travel.common.exception.UnknownException;

public class InputView {
    private final BufferedReader br;

    public InputView() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String inputString(Predicate<String> isValid, String errorMessage) {
        while (true) {
            String input = readLine();

            if (isValid.test(input)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    public String inputNotEmptyString(Predicate<String> isValid, String errorMessage) {
        while (true) {
            String input = inputString(isValid, errorMessage);
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("빈 칸으로는 입력할 수 없습니다. 다시 입력해주세요");
        }
    }

    public String inputNotEmptyString() {
        return inputNotEmptyString(str -> true, "");
    }

    public Menu inputMenu() {
        try {
            int menuNumber = inputNumber("잘못된 메뉴 번호입니다. 다시 입력해주세요;");
            return Menu.fromNumber(menuNumber);
        } catch (IllegalArgumentException e) {
            return inputMenu();
        }
    }

    public int inputNumber(String errorMessage) {
        while (true) {
            try {
                return parseInt(readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(errorMessage);
            }
        }
    }

    public int parseInt(String strNum) {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    public String readLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("입력을 받는 도중 알 수 없는 에러가 발생했습니다.");
            throw new UnknownException();
        }
    }
}
