package kr.co.fastcampus.travel.domain;

public enum FileType {

    CSV(1), JSON(2);
    private final int number;

    FileType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
