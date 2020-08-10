package com.fp.cloud.main.global.payload;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorJson {

    private Integer code;
    private String error;
    private String message;
    private String timeStamp;
    private String trace;

    public ErrorJson(int code, Map<String, Object> errorAttributes) {
        this.code = code;
        this.error = (String) errorAttributes.get("error");
        this.message = (String) errorAttributes.get("message");
        this.timeStamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
    }
}
