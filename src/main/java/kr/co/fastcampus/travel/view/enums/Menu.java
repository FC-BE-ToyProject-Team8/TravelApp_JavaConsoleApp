package kr.co.fastcampus.travel.view.enums;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Menu {
    LOG_TRIP(1, "여행기록"), LOG_ITINERARY(2, "여정기록"),
    SHOW_TRIP(3, "여행조회"), SHOW_ITINERARY(4, "여정조회"),
    EXIT(5, "종료");

    private final int number;
    private final String name;

    public static Menu fromNumber(int number) {
        try {
            return Arrays.stream(Menu.values())
                .filter(menu -> menu.number == number)
                .findFirst()
                .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

}
