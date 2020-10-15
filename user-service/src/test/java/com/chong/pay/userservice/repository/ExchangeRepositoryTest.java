package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.exchange.Exchange;
import com.chong.pay.userservice.domain.exchange.ShopPayment;
import com.chong.pay.userservice.domain.exchange.Transfer;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log
class ExchangeRepositoryTest {

    @Autowired
    ExchangeRepository exchangeRepository;

    @Test
    void exchangeSaveTest(){
        Exchange exchange1 = ShopPayment.builder()
                .exchangeId("1").exchangeDate(LocalDateTime.now()).money(1000L)
                .userId("chong").shopId("coffee1").build();
        Exchange exchange2 = Transfer.builder()
                .exchangeId("2").exchangeDate(LocalDateTime.now()).money(2000L)
                .sendUser("woo").receiveUser("chong").isComplete(false).build();

        exchangeRepository.save(exchange1).block();
        exchangeRepository.save(exchange2).block();

        Flux<Exchange> result = exchangeRepository.findAll();

        StepVerifier.create(result)
                .consumeNextWith(exchange -> {
                    log.info("저장된 자료: " + exchange.toString());
                }).consumeNextWith(exchange -> {
                    log.info("저장된 자료: " + exchange.toString());
                }).expectComplete().verify();

        exchangeRepository.deleteById("1").subscribe();
        exchangeRepository.deleteById("2").subscribe();
    }

}