package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.Shop;
import com.chong.pay.userservice.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopInformationServiceTest {

    @Autowired
    ShopInformationService shopInformationService;

    @Autowired
    ShopRepository shopRepository;

    @Test
    void showShopInfo() {
        Shop shop = Shop.builder()
                .shopId("naver").payMoney(10000L).build();
        shopRepository.save(shop);

        Shop result = shopInformationService.showShopInfo("naver");
        assertEquals(result.getShopId(), "naver");
    }
}