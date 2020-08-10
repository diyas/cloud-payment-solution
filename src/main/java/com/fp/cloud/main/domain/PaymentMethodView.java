package com.fp.cloud.main.domain;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Immutable
@Table(name = "payment_method_view")
public class PaymentMethodView {
    @Id
    @Column
    private int id;
    @Column(name = "method_name")
    private String methodName;
    @Column(name = "payment_name")
    private String paymentName;
    @Column(name = "created_date")
    private Date createdDate;

    public String getQrName() {
        if (getMethodName().equalsIgnoreCase("QR")) {
            return getPaymentName();
        }
        return "";
    }
}
