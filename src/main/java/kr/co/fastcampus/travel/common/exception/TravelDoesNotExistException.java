package kr.co.fastcampus.travel.common.exception;

public class TravelDoesNotExistException extends BaseException {

    public TravelDoesNotExistException() {
        super(ErrorCode.TRAVEL_DOES_NOT_EXIST);
    }
}
