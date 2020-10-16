package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.Exchange;
import com.chong.pay.userservice.repository.ExchangeRepository;
import com.sun.tools.javac.util.List;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log
class UserInformationServiceTest {

    @Autowired
    UserInformationService userInformationService;

    @Autowired
    ExchangeRepository exchangeRepository;

    @Test
    void findAllExchangesTest(){
        Exchange exchange3 = Exchange.builder()
                .paymentId(3L).myId("chong").otherId("kakao").exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
                .money(9000L).exchangeDate(LocalDateTime.of(2020, 5, 1, 0, 0)).build();
        Exchange exchange1 = Exchange.builder()
                .paymentId(1L).myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
                .money(10000L).exchangeDate(LocalDateTime.of(2020, 1, 1, 0, 0)).isComplete(false).build();
        Exchange exchange2 = Exchange.builder()
                .paymentId(2L).myId("chong").otherId("kakao").exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
                .money(9000L).exchangeDate(LocalDateTime.of(2020, 3, 1, 0, 0)).build();

        exchangeRepository.saveAll(List.of(exchange2, exchange1, exchange3)).blockLast();

        StepVerifier.create(userInformationService.findAllExchangeOfOne("chong"))
                .consumeNextWith(exchange->log.info(exchange.toString()))
                .consumeNextWith(exchange->log.info(exchange.toString()))
                .consumeNextWith(exchange->log.info(exchange.toString()))
                .expectComplete()
                .verify();

        exchangeRepository.deleteAll().subscribe();
    }



}