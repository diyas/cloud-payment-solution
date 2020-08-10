package com.fp.cloud.main.domain;

import lombok.Data;

@Data
//@Entity
//@Immutable
//@Table(name = "payment_transaction_view")
public class PaymentTransactionView {
    //@Column(name = "payment_status")
    private String paymentStatus;
    //@Column(name = "payment_type")
    private String paymentType;
    //@Column(name = "pos_request_type")
    private String posRequestType;
    //@Column(name = "pos_cloud_pointer")
    private String posCloudPointer;
    //@Column(name = "db_timestamp")
    private String dbTimeStamp;
    //@Column(name = "pos_cloud_push_notif_flag")
    private String posCloudPushNotifFlag;
}
