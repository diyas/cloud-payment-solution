package com.fp.cloud.main.repository;

import com.fp.cloud.main.domain.Transaction;
import com.fp.cloud.main.domain.TransactionHistory;
import com.fp.cloud.main.global.TrStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory, Long> {
    TransactionHistory findByTrNo(String trNo);

    TransactionHistory findByTrNoAndTrStatus(String trNo, TrStatusEnum trStatus);
}
