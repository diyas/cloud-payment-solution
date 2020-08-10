package com.fp.cloud.main.global.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestSettlement {
    @ApiModelProperty(
            notes = "1 = Credit/Debit, " +
                    "2 = QR "
    )
    private int trMethod;
    @ApiModelProperty(notes = "Repeating Publishing")
    private boolean republish;
}
