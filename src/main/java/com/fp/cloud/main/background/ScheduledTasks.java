package com.fp.cloud.main.background;

import com.fp.cloud.main.domain.Transaction;
import com.fp.cloud.main.domain.TransactionHistory;
import com.fp.cloud.main.global.TrStatusEnum;
import com.fp.cloud.main.repository.TransactionHistoryRepo;
import com.fp.cloud.main.repository.TransactionRepo;
import com.fp.cloud.main.service.SyncClientService;
import com.fp.cloud.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.UnaryOperator;

@Slf4j
@Component
public class ScheduledTasks {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private TransactionHistoryRepo transactionHistoryRepo;

    @Autowired
    private SyncClientService syncClientService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(cron = "*/10 * * * * *")
//    public void copyData() {
//        copyDataTransaction();
//    }

//    @Scheduled(cron = "*/10 * * * * *")
//    public void syncData() {
//        syncClientService.sync();
//    }

    private void copyDataTransaction(){
        Collection c = Arrays.asList(TrStatusEnum.PENDING, TrStatusEnum.SUCCESS);
        List<Transaction> lstTrx = transactionRepo.findAllByTrDateAndTrStatusIn(Utility.getDate(), c);
        log.info("trxSize: "+lstTrx.size());
        log.info("data: "+lstTrx);
        if (lstTrx.size() != 0){
            transactionHistoryRepo.saveAll(genHistory(lstTrx));
            transactionRepo.deleteAll(lstTrx);
        }
    }

    private List<TransactionHistory> genHistory(List<Transaction> transactions){
        List<TransactionHistory> lst = new ArrayList<>();
        for(Transaction transaction : transactions){
            TransactionHistory tHistory = new TransactionHistory();
            tHistory.setTrNo(transaction.getTrNo());
            tHistory.setInvoiceNo(transaction.getInvoiceNo());
            tHistory.setTrMethod(transaction.getTrMethod());
            tHistory.setTrAmount(transaction.getTrAmount());
            tHistory.setTrDate(transaction.getTrDate());
            tHistory.setTrRequestDate(transaction.getTrRequestDate());
            tHistory.setTrResponseDate(transaction.getTrResponseDate());
            tHistory.setTrTopicPos(transaction.getTrTopicPos());
            tHistory.setTrTopicEdc(transaction.getTrTopicEdc());
            tHistory.setTrStatus(transaction.getTrStatus());
            tHistory.setUserId(transaction.getUserId());
            tHistory.setTrNoPos(transaction.getTrNoPos());
            tHistory.setTrType(transaction.getTrType());
            lst.add(tHistory);
        }
        return lst;
    }
}
