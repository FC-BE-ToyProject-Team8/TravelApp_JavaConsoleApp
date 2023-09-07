package kr.co.fastcampus.travel.common.exception;

public class UnknownException extends BaseException {

    public UnknownException() {
        super(ErrorCode.UNKNOWN_ERROR);
    }

    public UnknownException(String message) {
        super(message, ErrorCode.UNKNOWN_ERROR);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, ErrorCode.UNKNOWN_ERROR, cause);
    }
}
