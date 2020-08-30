package com.fp.cloud.main.global.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestVoid {
    @ApiModelProperty(notes = "Invoice Number/Trace Number")
    private String invoiceNumber;
}
