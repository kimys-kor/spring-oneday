package com.oneday.api.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {


    // 정상 처리 0XXXXXXX
    // DATA 관련 010001XX
    DATA_SEARCH_SUCCESSFULLY(HttpStatus.OK, "00001100", "정상 데이터 입니다."),
    DATA_NORMAL_PROCESSING(HttpStatus.OK, "00001101", "정상 처리 되었습니다."),

    // USER 관련 010002XX
    USER_JOIN_SUCCESSFULLY(HttpStatus.OK, "00001001", "회원가입이 완료되었습니다."),
    USER_NOT_DUPLICATE_ID(HttpStatus.OK, "00001002", "중복된 아이디가 아닙니다."),
    USER_NOT_DUPLICATE_NICKNAME(HttpStatus.OK, "00001003", "중복된 닉네임이 아닙니다."),


    // 비정상 처리 91XXXXXX
    AUTH_PERMISSION_DENY(HttpStatus.OK, "91009991", "접근 권한이 없습니다."),

    // DATA 관련 910001XX
    DATA_UPDATE_WRONG(HttpStatus.OK, "91000101", "데이터 수정에 실패하였습니다."),
    DATA_NOT_DUPLICATE(HttpStatus.OK, "91000102", "중복된 DATA 입니다."),
    DATA_ALREADY_PROCESSED(HttpStatus.OK, "91000103", "이미 처리된 DATA 입니다."),


    // USER 관련 910002XX
    USER_NO_ID_SEARCHED(HttpStatus.OK, "91000201", "검색된 아이디가 없습니다."),
    USER_NOT_USE_ACCOUNT(HttpStatus.OK, "91000202", "비활성화 계정 입니다."),
    USER_JOIN_EMPTY_ACCOUNT(HttpStatus.OK, "91000203", "아이디를 입력하여 주시기 바랍니다."),
    USER_JOIN_EMPTY_PWD(HttpStatus.OK, "91000204", "비밀번호를 입력하여 주시기 바랍니다."),
    USER_JOIN_RESUBSCRIPTION_IS_NOT_VALID(HttpStatus.OK, "91000214", "탈퇴 후 재가입 유효기간이 아닙니다."),
    USER_JOIN_DUPLICATE_ACCOUNT(HttpStatus.OK, "91000215", "중복된 계정 입니다."),
    USER_JOIN_DUPLICATE_EMAIL(HttpStatus.OK, "91000216", "중복된 이메일 입니다."),
    USER_JOIN_EMAIL_NOT_AVAILABLE(HttpStatus.OK, "91000217", "사용할 수 없는 이메일 입니다."),
    USER_JOIN_DUPLICATE_PHONENUMBER(HttpStatus.OK, "91000218", "중복된 핸드폰 번호 입니다."),
    USER_JOIN_PHONENUMBER_NOT_AVAILABLE(HttpStatus.OK, "91000219", "사용할 수 없는 핸드폰 번호 입니다."),
    USER_INFORMATION_IS_NOT_RIGHT(HttpStatus.OK, "91000221", "로그인정보가 옳바르지 않습니다."),
    USER_TOKEN_ID_IS_NOT_RIGHT(HttpStatus.OK, "91000222", "토큰 ID가 옳바르지 않습니다."),
    USER_NO_SEARCHED(HttpStatus.OK, "91000223", "검색된 고객이 없습니다."),
    USER_JOIN_DUPLICATE_NICKNAME(HttpStatus.OK, "91000224", "중복된 닉네임 입니다."),
    USER_JOIN_IS_NULL(HttpStatus.OK, "91000225", "닉네임을 입력하여 주시기 바랍니다."),
    USER_PHONENUM_NO_SEARCHED(HttpStatus.OK, "91000226", "가입되지 않은 휴대폰 번호입니다."),
    USER_PHONENUM_EMAIL_NOT_MATCH(HttpStatus.OK, "91000227", "이메일과 폰번호가 일치하지 않습니다."),

    // APP 관련 910003XX
    APP_CODE_IS_NOT_RIGHT(HttpStatus.OK, "91000301", "App Code가 옳바르지 않습니다."),
    APP_VERSION_SEARCH_FAIL(HttpStatus.OK, "91000302", "App Version 조회에 실패하였습니다."),
    APP_CODE_DUPLICATE(HttpStatus.OK, "91000303", "중복된 App Code 입니다."),

    APP_VERSION_DUPLICATE(HttpStatus.OK, "91000303", "중복된 App 입니다."),

    // Point 관련 910004XX
    LACK_OF_POINTS(HttpStatus.OK, "91000401", "Point 부족 합니다."),
    PURCHASE_THE_PREVIOUS_PRODUCT_LACK_OF_POINTS(HttpStatus.OK, "91000402", "이전 상품권 구매로 인한 현재 Point 부족 합니다."),
    POINT_GIFT_USER_NOT_QUALIFIED(HttpStatus.OK, "91000403", "포인트 받을 권한이 없는 고객입니다."),

    // GiftLetter 관련 910005XX
    GIFTLETTER_GOODS_SEARCH_FAIL(HttpStatus.OK, "91000501", "상품 조회에 실패 하였습니다."),
    PURCHASE_PRODUCT_FAIL(HttpStatus.OK, "91000502", "상품 구매에 실패 하였습니다.");



    private final HttpStatus status;
    private final String statusCode;
    private final String statusMessage;

    ResultCode(HttpStatus status, String statusCode, String statusMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }


}
