package com.oneday.api.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {


    // 정상 처리 0XXXXXXX
    // DATA 관련 010001XX
    DATA_NORMAL_PROCESSING(HttpStatus.OK, "00001101", "정상 처리 되었습니다."),

    // USER 관련 010002XX
    USER_JOIN_SUCCESSFULLY(HttpStatus.OK, "00001001", "회원가입이 완료되었습니다."),
    USER_NOT_DUPLICATE_ID(HttpStatus.OK, "00001002", "중복된 아이디가 아닙니다."),
    USER_NOT_DUPLICATE_NICKNAME(HttpStatus.OK, "00001003", "중복된 닉네임이 아닙니다.");


    private final HttpStatus status;
    private final String statusCode;
    private final String statusMessage;

    ResultCode(HttpStatus status, String statusCode, String statusMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }


}
