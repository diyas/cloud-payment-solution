package com.fp.cloud.main.global.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestPayment {
    @ApiModelProperty(notes = "Transaction Number from POS")
    private String trNoPos;
    @ApiModelProperty(
            notes = "SALE, VOID, SETTLEMENT"
    )
    private String trType;
    @ApiModelProperty(
            notes = "1 = Credit/Debit, " +
                    "2 = DANA, " +
                    "3 = OVO, " +
                    "4 = GOPAY, " +
                    "5 = LINKAJA, " +
                    "6 = CASH, " +
                    "7 = SPLIT"
    )
    private int trMethod;
    @ApiModelProperty(notes = "Base Amount")
    private Double trAmount;
    @ApiModelProperty(notes = "Repeating Publishing")
    private boolean republish;
}
