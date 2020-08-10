package com.fp.cloud.configuration.oauth;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class Oauth2ResponseError implements Serializable {

    private String error;
    @SerializedName(value = "error_description")
    private String errorDescription;
}
