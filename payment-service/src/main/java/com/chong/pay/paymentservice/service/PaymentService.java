package com.chong.pay.paymentservice.service;

import com.chong.pay.paymentservice.domain.*;
import com.chong.pay.paymentservice.repository.ExchangeRepository;
import com.chong.pay.paymentservice.repository.PayUserRepository;
import com.chong.pay.paymentservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log
public class PaymentService {

    private final PointValidator pointValidator;
    private final ExchangeRepository exchangeRepository;
    private final PayUserRepository payUserRepository;
    private final ShopRepository shopRepository;

    public PaymentResponse runPayment(PaymentRequest paymentRequest){
        updateExchangeRecord(paymentRequest, exchangeRepository);
        PayUser userResult = updatePayUser(paymentRequest, validatePointAndGetMoneytoPay(paymentRequest, pointValidator), payUserRepository);
        Shop shopResult = updateShop(paymentRequest, shopRepository);

        return PaymentResponse.builder()
                .userId(paymentRequest.getUserId())
                .shopName(shopResult.getShopName())
                .money(userResult.getPayMoney()).build();
    }

    private static int validatePointAndGetMoneytoPay(PaymentRequest paymentRequest, PointValidator pointValidator){
        if(!paymentRequest.isDoesUsePoint()) { return paymentRequest.getMoney(); }

        if(!pointValidator.validateUserPoint(paymentRequest.getUserId(), paymentRequest.getUsingPoint())) {
            return paymentRequest.getMoney();
        }

        return paymentRequest.getMoney() - paymentRequest.getUsingPoint();
    }

    private static void updateExchangeRecord(PaymentRequest paymentRequest, ExchangeRepository exchangeRepository){
        Exchange exchange = Exchange.builder()
                .paymentId(exchangeIdGenerator(paymentRequest.getDateTime()))
                .myId(paymentRequest.getUserId())
                .otherId(paymentRequest.getShopId())
                .exchangeDate(paymentRequest.getDateTime())
                .exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
                .money(paymentRequest.getMoney())
                .build();

        exchangeRepository.save(exchange);
    }

    private static String exchangeIdGenerator(LocalDateTime dateTime){
        return String.valueOf(dateTime.getYear()) +
                dateTime.getMonthValue() +
                dateTime.getDayOfMonth() +
                UUID.randomUUID().toString().substring(0, 7);
    }

    private static PayUser updatePayUser(PaymentRequest paymentRequest, int toPay, PayUserRepository payUserRepository){
        PayUser result = payUserRepository.findById(paymentRequest.getUserId()).orElseThrow(NoSuchFieldError::new);
        result.setPayMoney(result.getPayMoney() - toPay);
        result.setPoint(result.getPoint() - paymentRequest.getUsingPoint());
        return payUserRepository.save(result);
    }

    private static Shop updateShop(PaymentRequest paymentRequest, ShopRepository shopRepository){
        Shop result = shopRepository.findById(paymentRequest.getShopId()).orElseThrow(NoSuchElementException::new);
        result.setPayMoney(result.getPayMoney() + paymentRequest.getMoney());
        return shopRepository.save(result);
    }
}
