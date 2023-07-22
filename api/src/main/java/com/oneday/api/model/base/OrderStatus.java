package com.oneday.api.model.base;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAITING("waiting"),
    PREPAIRING("preparing"),
    CANTAKE("cantake"),
    INDELIVERY("indelivery"),
    COMPLETE("complete"),
    CANCEL("cancel"),
    CHANGE("change")
    ;

    private final String code;

    OrderStatus(String code) {
        this.code = code;
    }


    public static OrderStatus of(String code) {
        if (code == null) {
            throw new IllegalStateException();
        }

        for (OrderStatus os : OrderStatus.values()) {
            if (os.code.equals(code)) {
                return os;
            }
        }
        throw new IllegalStateException("일치하는 주문상태 코드가 없습니다.");
    }

}
