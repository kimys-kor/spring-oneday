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


    public static OrderStatus of(String status) {
        if (status == null) {
            throw new IllegalStateException();
        }

        for (OrderStatus st : OrderStatus.values()) {
            if (st.code.equals(status)) {
                return st;
            }
        }
        throw new IllegalStateException("일치하는 주문상태 코드가 없습니다.");
    }

}
