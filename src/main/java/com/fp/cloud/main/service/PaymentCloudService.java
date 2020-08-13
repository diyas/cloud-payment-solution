package com.fp.cloud.main.service;

import com.fp.cloud.configuration.mqtt.MqttService;
import com.fp.cloud.main.domain.PaymentMethodView;
import com.fp.cloud.main.domain.Settlement;
import com.fp.cloud.main.domain.Transaction;
import com.fp.cloud.main.global.TrStatusEnum;
import com.fp.cloud.main.global.payload.*;
import com.fp.cloud.main.repository.PaymentMethodRepo;
import com.fp.cloud.main.repository.SettlementRepo;
import com.fp.cloud.main.repository.TransactionRepo;
import com.fp.cloud.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PaymentCloudService {

    private String pointerCode = "CLD";

    @Autowired
    private MqttService mqttService;

    @Autowired
    private TransactionRepo trRepo;

    @Autowired
    private PaymentMethodRepo payRepo;

    @Autowired
    private SettlementRepo setRepo;


    public ResponseEntity<String> postSale(RequestPayment request) throws MqttException {
        boolean isNew = false;
        Transaction tr = null;
        PaymentMethodView method = payRepo.findById(request.getTrMethod());
        mqttService.connect();
        if (request.getTrNoPos() != null)
            tr = trRepo.findByUserIdAndTrNoPosAndTrStatus(Utility.getUser(), request.getTrNoPos(), TrStatusEnum.PENDING);
        if (tr == null){
            tr = new Transaction();
            tr.setTrNo(pointerCode + "-" + Utility.getUser() + "-" + System.currentTimeMillis());
            tr.setUserId(Utility.getUser());
            tr.setTrAmount(request.getTrAmount());
            tr.setTrMethod(request.getTrMethod());
            tr.setTrTopicEdc("payment/pos/status/" + method.getId() + "/" + Utility.getUser());
            tr.setTrTopicPos("payment/pos/" + Utility.getUser());
            tr.setTrStatus(TrStatusEnum.PENDING);
            tr.setTrNoPos(request.getTrNoPos());
            isNew = true;
        }
        Transaction trResult = null;
        if (mqttService.isConnected() && isNew)
            trResult = trRepo.save(tr);
        if (trResult != null || isNew || request.isRepublish()) {
            String msg = Utility.objectToString(new MessageParam(method.getPaymentName().toLowerCase(), tr.getTrAmount(), tr.getTrNo()));
            mqttService.publish(tr.getTrTopicPos(), msg);
        }
        else {
            if (isNew) {
                return Utility.setResponse("payment failed published", null);
            } else {
                return Utility.setResponse("payment failed published, "+tr.getTrNoPos()+" already exists", tr);
            }
        }
        mqttService.disconnect();
        return Utility.setResponse("payment has been published", tr);
    }

    public ResponseEntity<String> updateStatusPayment(RequestUpdateStatus reqUpdate) throws MqttException{
        Transaction tr = trRepo.findByTrNoAndTrStatus(reqUpdate.getTrNo(), TrStatusEnum.PENDING);
        if (tr == null)
            return Utility.setResponse("payment status success", null);
        tr.setTrStatus(TrStatusEnum.SUCCESS);
        tr.setInvoiceNo(reqUpdate.getInvoiceNo());
        trRepo.save(tr);
        if (tr.getTrNo() != "0") {
            mqttService.connect();
            mqttService.publish(tr.getTrTopicPos(), Utility.objectToString(tr));
            mqttService.disconnect();
        }
        return Utility.setResponse("payment has been success", tr);
    }

    public void cancelTransaction(String trNo) throws Exception {
        final long start = System.currentTimeMillis();
        Transaction tr = trRepo.findByTrNoPosAndTrStatus(trNo, TrStatusEnum.PENDING);
        tr.setTrStatus(TrStatusEnum.CANCEL);
        trRepo.save(tr);
        log.info("Transaction ID "+ tr.getTrNoPos());
    }

    public void postVoid(){

    }

    public ResponseEntity<String> postSettlement(RequestSettlement req) throws MqttException{
        boolean isNew = true;
        mqttService.connect();
        Settlement st = new Settlement();
        st.setId(pointerCode+"-S"+ "-" + Utility.getUser() + "-" + System.currentTimeMillis());
        st.setStatus(TrStatusEnum.PENDING);
        st.setTopic("pos/settlement/"+req.getTrMethod()+"/"+Utility.getUser());
        Settlement stResult = null;
        if (mqttService.isConnected())
            stResult = setRepo.save(st);
        if (stResult != null) {
            String msg = Utility.objectToString(stResult);
            mqttService.publish(st.getTopic(), msg);
        }
        else {
            if (isNew) {
                return Utility.setResponse("settlement failed published", null);
            } else {
                return Utility.setResponse("settlement failed published, "+st.getId()+" already exists", st);
            }
        }
        mqttService.disconnect();
        return Utility.setResponse("settlement has been published", stResult);

    }

    public ResponseEntity<String> updateStatusSettlement(String id) throws MqttException{
        Settlement s = setRepo.findByIdAndStatus(id, TrStatusEnum.PENDING);
        if (s == null)
            return Utility.setResponse("settlement status completed", null);
        s.setStatus(TrStatusEnum.COMPLETED);
        setRepo.save(s);
        if (s.getId() != "0") {
            mqttService.connect();
            mqttService.publish(s.getTopic(), Utility.objectToString(s));
            mqttService.disconnect();
        }
        return Utility.setResponse("settlement has been success", s);
    }

    public ResponseEntity<String> settlementStatus(String id){
        Settlement s = setRepo.findById(id);
        if (s == null)
            return Utility.setResponse(TrStatusEnum.INVALID.toString(), null);
        return Utility.setResponse(s.getStatus().toString(), s);
    }

    public ResponseEntity<String> historyQr(RequestHistorySettlement date) {
        List<Transaction> transactionList =  trRepo.findByTrMethodIsNotAndAndUserIdAndTrStatusAndTrDate(1, Utility.getUser(), TrStatusEnum.SUCCESS, Utility.parseDate(date.getToDate(), "yyyy-MM-dd"));
        Double total = transactionList.stream().filter(x -> x.getTrAmount() > transactionList.size()).mapToDouble(x -> x.getTrAmount()).sum();
        TransactionSummary trSum = new TransactionSummary();
        trSum.setSummaryDate(Utility.getDate().toString());
        trSum.setAllTransaction(transactionList);
        trSum.setTotalAmount(total);
        if (transactionList.size() == 0)
            return Utility.setResponse("No Transaction List", null);
        else
            return Utility.setResponse("QR Success Payment List", trSum);
    }
}
