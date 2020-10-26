package com.chong.pay.paymentservice.controller;

import com.chong.pay.paymentservice.domain.PaymentRequest;
import com.chong.pay.paymentservice.domain.PaymentResponse;
import com.chong.pay.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> doPayment(@RequestBody PaymentRequest paymentRequest){
        PaymentResponse result = paymentService.runPayment(paymentRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
