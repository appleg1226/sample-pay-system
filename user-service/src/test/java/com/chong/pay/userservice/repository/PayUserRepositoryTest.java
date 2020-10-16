package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.PayUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log
class PayUserRepositoryTest {

    @Autowired
    PayUserRepository repository;

    @Test
    void mongoConnectionTest(){
        PayUser payUser1 = PayUser.builder()
                .userId("1")
                .email("dmschd92@naver.com")
                .payMoney(0L)
                .chargeMethods(new HashMap<>())
                .build();


        PayUser originalResult = repository.save(payUser1).block();

        Mono<PayUser> foundResult = repository.findById("1");

        StepVerifier.create(foundResult)
                .consumeNextWith(payUser -> {
                    log.info(originalResult.toString());
                    log.info(payUser.toString());
                    assertEquals(originalResult, payUser);
                }).expectComplete()
                .verify();

        repository.deleteById("1").subscribe();
    }
}
