package kr.co.fastcampus.travel.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    CSV(1), JSON(2);

    private final int number;

    public static FileType fromNumber(int number) {
        try {
            return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.number == number)
                .findFirst()
                .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
    }
}
