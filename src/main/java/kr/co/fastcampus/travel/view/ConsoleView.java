package kr.co.fastcampus.travel.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;
import kr.co.fastcampus.travel.common.exception.UnknownException;
import java.util.function.Predicate;

public class ConsoleView {

	private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private boolean isExited = false;

	public void process() {
		System.out.println("[메뉴]\n" + "1: 여행기록, 2: 여정기록, 3: 여행조회, 4: 여정조회, 5: 종료\n");
		System.out.println("메뉴 번호를 입력해주세요");

		int menuNum = inputNumber("잘못된 메뉴 번호입니다. 다시 입력해주세요",
			(num) -> num >= Menu.MIN.getNumber() && num <= Menu.MAX.getNumber());
		if (menuNum == Menu.LOG_TRIP.getNumber()) {
			logTrip();
		}

		isExited = true;
	}

	private void logTrip() {
		System.out.println("여행 기록을 시작합니다.");

	}

	private int inputNumber(String errorMessage, Predicate<Integer> isValid) {
		String strNum;
		int num = 0;
		while (true) {
			strNum = readLine();
			if (canParseInt(strNum) && isValid.test(Integer.parseInt(strNum))) {
				num = Integer.parseInt(strNum);
				break;
			}
			System.out.println(errorMessage);
		}

		return num;
	}

	private boolean canParseInt(String strNum) {
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private String readLine() {
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println("입력을 받는 도중 알 수 없는 에러가 발생했습니다.");
			throw new RuntimeException();
		}
	}

	public boolean isExited() {
		return isExited;
	}
