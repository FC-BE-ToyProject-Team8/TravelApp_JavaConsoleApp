package kr.co.fastcampus.travel.common.response;

import static kr.co.fastcampus.travel.common.response.ErrorCode.COMMON_SYSTEM_ERROR;
import static kr.co.fastcampus.travel.common.response.ResultCode.ERROR;
import static kr.co.fastcampus.travel.common.response.ResultCode.FAIL;
import static kr.co.fastcampus.travel.common.response.ResultCode.SUCCESS;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonResponse<T> {

    private ResultCode status;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse<>(SUCCESS, message, data);
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(null, data);
    }

    public static CommonResponse<String> error() {
        return new CommonResponse<>(ERROR, COMMON_SYSTEM_ERROR.getErrorMsg(), COMMON_SYSTEM_ERROR.name());
    }

    public static CommonResponse<String> fail(String message, ErrorCode errorCode) {
        return new CommonResponse<>(FAIL, message, errorCode.name());
    }

    public static CommonResponse<String> fail(ErrorCode errorCode) {
        return fail(errorCode.getErrorMsg(), errorCode);
    }
}
