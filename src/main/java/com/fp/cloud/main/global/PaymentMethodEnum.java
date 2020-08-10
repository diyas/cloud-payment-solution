package com.fp.cloud.main.global;

public enum PaymentMethodEnum {
    CREDITDEBIT(1,"Credit/Debit"),
    DANA(2,"DANA"),
    OVO(3,"OVO"),
    GOPAY(4,"GOPAY"),
    LINKAJA(5,"LINKAJA"),
    CASH(6,"CASH"),
    SPLIT(7,"SPLIT"),
    UNKNOWN(0,"");

    public final int code;
    public final String label;

    private PaymentMethodEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    private PaymentMethodEnum getById(Long id) {
        for(PaymentMethodEnum e : PaymentMethodEnum.values()) {
            if(e.code == id) return e;
        }
        return PaymentMethodEnum.UNKNOWN;
    }
}
