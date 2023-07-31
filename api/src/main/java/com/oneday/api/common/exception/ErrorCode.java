package com.oneday.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INDEX_NOT_FOUND(HttpStatus.OK,1001, "인덱스가 존재하지 않습니다."),
    BOARD_NOT_FOUND(HttpStatus.OK,1002, "게시글을 찾을 수 없습니다."),
    UNKNOWN_ERROR(HttpStatus.OK,1003, "토큰이 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.OK,1004, "유효한 토큰이 아닙니다."),
    EXPIRED_TOKEN(HttpStatus.OK,1005, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.OK,1006, "변조된 토큰입니다."),
    ACCESS_DENIED(HttpStatus.OK,1007, "권한이 없습니다."),
    INVALID_PARAMETER(HttpStatus.OK,1008, "파라미터 형식이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.OK,1008, "내부 서버 에러"),
    ;

    public final HttpStatus status;
    public final int code;
    public final String message;

    ErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}