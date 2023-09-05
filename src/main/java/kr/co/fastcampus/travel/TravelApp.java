package kr.co.fastcampus.travel;

import kr.co.fastcampus.travel.view.ConsoleView;

public class TravelApp {
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        while (!consoleView.isExited()) {
            consoleView.process();
        }
    }
}
