package com.fp.cloud.main.global.payload;

import com.fp.cloud.main.domain.Transaction;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TransactionSummary {
    private String summaryDate;
    private List<Transaction> allTransaction;
    private Double totalAmount;
}
