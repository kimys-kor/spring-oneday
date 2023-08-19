package com.oneday.api.common.exception;

import com.oneday.api.common.exception.inteface.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticationErrorCode implements ErrorCode {
    UNKNOWN_ERROR(HttpStatus.FORBIDDEN, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효한 토큰이 아닙니다."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.FORBIDDEN, "변조된 토큰입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 형식이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 에러"),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "유저 인증에 실패하였습니다"),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 ID입니다"),
    ;

    public final HttpStatus status;
    public final String message;

    @Override
    public HttpStatus defaultHttpStatus() {
        return status;
    }

    @Override
    public String defaultMessage() {
        return message;
    }

    @Override
    public AuthenticationException defaultException() {
        return new AuthenticationException(this);
    }

    @Override
    public AuthenticationException defaultException(Throwable cause) {
        return new AuthenticationException(this, cause);
    }
}
