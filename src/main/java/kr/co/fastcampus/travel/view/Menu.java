package kr.co.fastcampus.travel.view;

public enum Menu {
    MIN(1), MAX(5),
    LOG_TRIP(1), LOG_ITINERARY(2), SHOW_TRIP(3), SHOW_ITINERARY(4), EXIT(5);

    private int number;

    Menu(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
