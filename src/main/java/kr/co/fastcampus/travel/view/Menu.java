package kr.co.fastcampus.travel.view;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Menu {
    LOG_TRIP(1), LOG_ITINERARY(2), SHOW_TRIP(3), SHOW_ITINERARY(4), EXIT(5);

    private int number;

    Menu(int number) {
        this.number = number;
    }

    public static Menu fromNumber(int number) {
        try {
            return Arrays.stream(Menu.values()).filter(menu -> menu.number == number).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }

    public int getNumber() {
        return number;
    }
}
