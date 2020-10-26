package com.chong.pay.paymentservice.service;

import com.chong.pay.paymentservice.domain.PayUser;
import com.chong.pay.paymentservice.domain.PaymentRequest;
import com.chong.pay.paymentservice.domain.PaymentResponse;
import com.chong.pay.paymentservice.domain.Shop;
import com.chong.pay.paymentservice.repository.PayUserRepository;
import com.chong.pay.paymentservice.repository.ShopRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PayUserRepository payUserRepository;
    @Autowired
    ShopRepository shopRepository;

    @BeforeEach
    void addDatas(){
        PayUser payUser1 = PayUser.builder().userId("chong").payMoney(20000).point(3000).build();
        Shop shop1 = Shop.builder().shopId("1111").shopName("eidiya").payMoney(0).build();
        payUserRepository.save(payUser1);
        shopRepository.save(shop1);
    }

    @Test
    void runPaymentTest() {
        String userId = "chong";
        String shopId = "1111";
        int money = 3500;

        PaymentResponse result = paymentService.runPayment(new PaymentRequest(userId, shopId, money, true, 1000, LocalDateTime.now()));

        assertEquals(payUserRepository.findById("chong").get().getPayMoney(), result.getMoney());
        System.out.println(payUserRepository.findById("chong").get().getPayMoney() + " / " + result.getMoney());
    }
}