package com.fp.cloud.main.domain;

import com.fp.cloud.main.global.TrStatusEnum;
import com.fp.cloud.main.global.TrTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String trNo;
    @Column
    private String invoiceNo;
    @Column
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
    @Column
    @ApiModelProperty(notes = "Base Amount")
    private Double trAmount;
    @Column
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(notes = "Transaction Date")
    private Date trDate;
    @Column
    @CreationTimestamp
    @ApiModelProperty(notes = "Request Payment Date")
    private Date trRequestDate;
    @Column
    @UpdateTimestamp
    @ApiModelProperty(notes = "Response Payment Date")
    private Date trResponseDate;
    @Column
    private String trTopicPos;
    @Column
    private String trTopicEdc;
    @Column
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(notes = "Payment Status")
    private TrStatusEnum trStatus;
    @Column
    private String userId;
    @Column
    private String trNoPos;
    @Column
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(notes = "Transaction Type")
    private TrTypeEnum trType;
}
