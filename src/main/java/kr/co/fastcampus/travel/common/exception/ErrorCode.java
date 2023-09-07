package kr.co.fastcampus.travel.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    TRAVEL_DOES_NOT_EXIST("존재하지 않는 여행입니다."),
    UNKNOWN_ERROR("알 수 없는 에러입니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
