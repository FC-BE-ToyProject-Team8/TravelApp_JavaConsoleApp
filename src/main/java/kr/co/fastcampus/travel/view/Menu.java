package kr.co.fastcampus.travel.view;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Menu {
    LOG_TRIP(1), LOG_ITINERARY(2), SHOW_TRIP(3), SHOW_ITINERARY(4), EXIT(5);

    private final int number;

    public static Menu fromNumber(int number) {
        try {
            return Arrays.stream(Menu.values()).filter(menu -> menu.number == number).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

}
