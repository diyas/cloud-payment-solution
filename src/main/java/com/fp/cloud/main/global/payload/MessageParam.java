package com.fp.cloud.main.global.payload;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class MessageParam implements Serializable {
    @SerializedName(value = "payment_method")
    private String paymentMethod;
    @SerializedName(value = "base_amount")
    private Double baseAmount;
    @SerializedName(value = "pos_cloud_pointer")
    private String cloudPointer;

    public MessageParam(String paymentMethod, Double baseAmount, String cloudPointer) {
        this.paymentMethod = paymentMethod;
        this.baseAmount = baseAmount;
        this.cloudPointer = cloudPointer;
    }
}
