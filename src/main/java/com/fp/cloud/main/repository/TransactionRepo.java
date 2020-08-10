package com.fp.cloud.main.repository;

import com.fp.cloud.main.domain.Transaction;
import com.fp.cloud.main.global.TrStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByTrNo(String trNo);
    List<Transaction> findAllByTrDateAndTrStatusIn(Date trDate, Collection<String> statusIn);
    Transaction findByUserIdAndTrNo(String userId, String trNo);
    Transaction findByTrNoPosAndTrStatus(String trNoPos, TrStatusEnum trStatus);
    //@Query(value = "select t from transaction t where t.userId = :userid and t.trMethod <> :method and t.trStatus = :status and t.trDate like %:trdate%", nativeQuery = true)
    List<Transaction> findByTrMethodIsNotAndAndUserIdAndTrStatusAndTrDate(int method, String userId, TrStatusEnum trStatus, Date trDate);
    List<Transaction> findByUserIdAndTrStatus(String userId, TrStatusEnum trStatus);
    Transaction findByTrNo(String trNo);
    Transaction findByTrNoAndTrStatus(String trNo, TrStatusEnum trStatus);
}
