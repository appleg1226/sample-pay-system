package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.charge.BankRegister;
import com.chong.pay.userservice.domain.charge.CardRegister;
import com.chong.pay.userservice.domain.Exchange;
import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.repository.ExchangeRepository;
import com.chong.pay.userservice.repository.PayUserRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log
class UserInformationServiceTest {

    @Autowired
    UserInformationService userInformationService;

    @Autowired
    ExchangeRepository exchangeRepository;

    @Autowired
    PayUserRepository payUserRepository;

    @BeforeEach
    void addSampleDatas(){
        PayUser payUser = PayUser.builder()
                .userId("chong").email("dmschd92@naver.com").payMoney(5000L)
                .chargeMethods(new HashSet<>())
                .cardCompanyNames(new HashSet<>())
                .bankCompanyNames(new HashSet<>())
                .build();
        payUserRepository.save(payUser);

        Exchange exchange1 = Exchange.builder()
                .paymentId(1L).myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
                .money(10000L).exchangeDate(LocalDateTime.of(2020, 1, 1, 0, 0)).isComplete(false).build();
        Exchange exchange2 = Exchange.builder()
                .paymentId(2L).myId("chong").otherId("kakao").exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
                .money(9000L).exchangeDate(LocalDateTime.of(2020, 3, 1, 0, 0)).build();
        Exchange exchange3 = Exchange.builder()
                .paymentId(3L).myId("chong").otherId("kakao").exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
                .money(9000L).exchangeDate(LocalDateTime.of(2020, 5, 1, 0, 0)).build();
        Exchange exchange4 = Exchange.builder()
                .paymentId(4L).myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
                .money(-3000L).exchangeDate(LocalDateTime.of(2020, 2, 1, 0, 0)).isComplete(true).build();
        Exchange exchange5 = Exchange.builder()
                .paymentId(5L).myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
                .money(6000L).exchangeDate(LocalDateTime.of(2020, 4, 1, 0, 0)).isComplete(false).build();

        exchangeRepository.saveAll(Arrays.asList(exchange2, exchange1, exchange3, exchange4, exchange5));
    }

    @Test
    void showUserInfoTest(){
        PayUser result = userInformationService.showUserInfo("chong");
        assertEquals(result.getUserId(), "chong");
    }

    @Test
    void findAllExchangesTest(){
        List<Exchange> result = userInformationService.findAllExchanges("chong");
        assertEquals(result.size(), 5);
    }

    @Test
    void findAllPaymentsTest(){
        List<Exchange> result = userInformationService.findAllPayments("chong");
        assertEquals(result.size(), 2);
    }

    @Test
    void findAllSendsTest(){
        List<Exchange> result = userInformationService.findAllSends("chong");
        assertEquals(result.size(), 3);
    }

    @Test
    void findAllSendsByOnlySendTest(){
        List<Exchange> result = userInformationService.findAllSendsByOnlySend("chong");
        assertEquals(result.size(), 1);
    }

    @Test
    void findAllSendsByOnlyReceiveTest(){
        List<Exchange> result = userInformationService.findAllSendsByOnlyReceive("chong");
        assertEquals(result.size(), 2);
    }

    @Test
    void findAllSendsNotCompletedTest(){
        List<Exchange> result = userInformationService.findAllSendsNotCompleted("chong");
        assertEquals(result.size(), 2);
    }

    @Test
    void cardRegisterTest(){
        CardRegister bdCard = CardRegister.builder()
                .cardCompany("BD").cardNum("1111").yaer(21).month(1).cvc(111).build();
        CardRegister mastarCard = CardRegister.builder()
                .cardCompany("MASTAR").cardNum("2222").yaer(21).month(2).cvc(111).build();
        CardRegister kakeoCard = CardRegister.builder()
                .cardCompany("KAKEO").cardNum("3333").yaer(21).month(3).cvc(111).build();
        CardRegister oriCard = CardRegister.builder()
                .cardCompany("ORI").cardNum("4444").yaer(21).month(4).cvc(111).build();
        BankRegister helloBank = BankRegister.builder()
                .bankName("HELLO").accountNo("12345678").build();
        BankRegister kcBank = BankRegister.builder()
                .bankName("KC").accountNo("87654321").build();

        userInformationService.registerChargeMethod("chong", bdCard);
        assertEquals(1, payUserRepository.findById("chong").orElseThrow(NoSuchElementException::new).getCardCompanyNames().size());

        userInformationService.registerChargeMethod("chong", mastarCard);
        assertEquals(2, payUserRepository.findById("chong").orElseThrow(NoSuchElementException::new).getCardCompanyNames().size());

        userInformationService.registerChargeMethod("chong", kakeoCard);
        assertEquals(3, payUserRepository.findById("chong").orElseThrow(NoSuchElementException::new).getCardCompanyNames().size());

        userInformationService.registerChargeMethod("chong", oriCard);
        assertEquals(4, payUserRepository.findById("chong").orElseThrow(NoSuchElementException::new).getCardCompanyNames().size());

        userInformationService.registerChargeMethod("chong", helloBank);
        assertEquals(1, payUserRepository.findById("chong").orElseThrow(NoSuchElementException::new).getBankCompanyNames().size());

        userInformationService.registerChargeMethod("chong", kcBank);
        assertEquals(2, payUserRepository.findById("chong").orElseThrow(NoSuchElementException::new).getBankCompanyNames().size());

        log.info(payUserRepository.findById("chong").orElseThrow(NoSuchElementException::new).toString());
    }

}