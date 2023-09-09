package kr.co.fastcampus.travel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.common.exception.UnknownException;
import kr.co.fastcampus.travel.domain.FileType;

public class InputView {
    private final BufferedReader br;

    public InputView() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public LocalDateTime inputDateTime() {
        System.out.println("날짜와 시간 입력 (Enter로 생략 가능, YYYY-MM-DD HH:MM 형식으로 입력):");
        String dateTimeStr = inputString(
            str -> str.isEmpty() || isValidDateTime(str),
            "0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요"
        );
        return dateTimeStr.isEmpty()
            ? null
            : LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public LocalDate inputDate() {
        System.out.println("날짜 입력 (YYYY-MM-DD 형식으로 입력):");
        String dateStr = inputNotEmptyString(
            this::isValidDate,
            "0000-00-00 날짜 형식에 맞지 않습니다. 다시 입력해주세요"
        );
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public FileType inputFileType() {
        try {
            int fileNum = inputNumber("잘못된 번호입니다. 다시 입력해주세요");
            return FileType.fromNumber(fileNum);
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 번호입니다. 다시 입력해주세요");
            return inputFileType();
        }
    }

    public Menu inputMenu() {
        System.out.println("[메뉴]");
        System.out.println(Arrays.stream(Menu.values())
            .map(menu -> String.format("%d: %s", menu.getNumber(), menu.getName()))
            .collect(Collectors.joining(", ")));

        System.out.println("\n메뉴 번호를 입력해주세요");

        while (true) {
            try {
                int menuNumber = inputNumber("잘못된 메뉴 번호입니다. 다시 입력해주세요;");
                return Menu.fromNumber(menuNumber);
            } catch (IllegalArgumentException e) {
                System.out.println("잘못된 메뉴 번호입니다. 다시 입력해주세요");
            }
        }
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

    public int inputNumber(String errorMessage) {
        while (true) {
            try {
                return parseInt(readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(errorMessage);
            }
        }
    }

    public int inputNumber(String errorMessage, Predicate<Integer> isValid) {
        while (true) {
            try {
                int num = parseInt(readLine());
                if (isValid.test(num)) {
                    return num;
                }
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println(errorMessage);
            }
        }
    }

    private boolean isValidDateTime(String dateTimeStr) {
        try {
            LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
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
