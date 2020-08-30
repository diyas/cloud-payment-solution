package com.fp.cloud.main.controller;

import com.fp.cloud.configuration.mqtt.MqttService;
import com.fp.cloud.main.domain.Transaction;
import com.fp.cloud.main.global.TrStatusEnum;
import com.fp.cloud.main.global.payload.*;
import com.fp.cloud.main.repository.PaymentMethodRepo;
import com.fp.cloud.main.repository.TransactionRepo;
import com.fp.cloud.main.service.PaymentCloudService;
import com.fp.cloud.utility.Utility;
import io.swagger.annotations.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
@Api(value = "/api", tags = "Cloud Payment")
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header") })
public class PaymentStatusCtl {

    @Autowired
    private TransactionRepo trRepo;

    @Autowired
    private PaymentCloudService listener;

    @PostMapping(value = "/v1/payment/publish")
    @ApiOperation(
            value = "Publish Payment", notes = "Subscribe Broker : payment/pos/{userid}",
            response = Response.class)
    public ResponseEntity<String> publishPayment(@ApiParam(value = "Request Body Parameter", required = true)
                                                     @RequestBody RequestPayment request) throws Exception {
        return listener.postSale(request);
    }

    @PutMapping(value = "/v1/payment")
    @ApiOperation(value = "Payment Response", notes = "Update Status transaction", response = Response.class)
    public ResponseEntity<String> updateStatusPayment(@RequestBody RequestUpdateStatus reqUpdate) throws MqttException {
        return listener.updateStatusPayment(reqUpdate);
    }

    @PutMapping(value = "/v1/payment/cancel/{tr_no}")
    @ApiOperation(value = "Payment Response", notes = "Cancel transaction", response = Response.class)
    public ResponseEntity<String> cancelTransaction(@PathVariable("tr_no") String trNo) throws Exception {
        return listener.cancelTransaction(trNo);
    }

//    @GetMapping(value = "/v1/payment/status/{trNo}")
//    @ApiOperation(value = "Check Payment Status", notes = "Check Payment Status", response = Response.class)
//    public ResponseEntity<String> getStatusPayment(@PathVariable String trNo) {
//        Transaction tr = trRepo.findByUserIdAndTrNo(Utility.getUser(), trNo);
//        if (tr == null)
//            return Utility.setResponse(TrStatusEnum.INVALID.toString(), null);
//        return Utility.setResponse(tr.getTrStatus().toString(), tr);
//    }

//    @PostMapping(value = "/v1/settlement")
//    @ApiOperation(value = "Settlement Request", notes = "Request Settlement Transaction", response = Response.class)
//    public ResponseEntity<String> reqSettlement(@ApiParam(value = "Request Body Parameter", required = true)
//                                                    @RequestBody RequestSettlement request) throws MqttException {
//        return listener.postSettlement(request);
//    }
//
//    @PutMapping(value = "/v1/settlement")
//    @ApiOperation(value = "Settlement Response", notes = "Response Settlement Transaction", response = Response.class)
//    public ResponseEntity<String> respSettlement(@ApiParam(value = "Request Id Parameter", required = true)
//                                                @PathVariable String reqId) throws MqttException {
//        return listener.updateStatusSettlement(reqId);
//    }
//
//    @GetMapping(value = "/v1/transaction/history")
//    @ApiOperation(value = "QR Transaction History", notes = "List QR Transaction Success", response = Response.class)
//    public ResponseEntity<String> historySettlement(@ApiParam(value = "Request Body Parameter", required = true)
//                                                        @RequestBody RequestHistorySettlement date) {
//        return listener.historyQr(date);
//    }

    @PostMapping(value = "/v1/transaction/void/publish")
    @ApiOperation(value = "Request Void Transaction", notes = "Request Void Transaction", response = Response.class)
    public ResponseEntity<String> publishVoid(@ApiParam(value = "Request Body Parameter", required = true)
                                                    @RequestBody RequestVoid requestVoid) throws MqttException {
        return listener.postVoidCDCP(requestVoid);
    }
}
