package kr.co.fastcampus.travel.controller.exception;

public class TravelDoesNotExistException extends RuntimeException {

    public TravelDoesNotExistException(String message) {
        super(message);
    }
}
