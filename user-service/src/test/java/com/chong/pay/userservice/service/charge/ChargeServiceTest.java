package com.chong.pay.userservice.service.charge;

import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.repository.PayUserRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log
class ChargeServiceTest {

    @Autowired
    ChargeService chargeService;

    @Autowired
    CardCharge cardCharge;

    @Autowired
    BankCharge bankCharge;

    @Autowired
    PayUserRepository payUserRepository;

    @Test
    void cardChargeSimulationTest(){
        Set<PayUser.ChargeMethod> chargeSet = new HashSet<>();
        chargeSet.add(PayUser.ChargeMethod.CARD);

        PayUser payUser = PayUser.builder()
                .userId("chong").email("dmschd92@naver.com").payMoney(5000L).chargeMethods(chargeSet).build();
        payUserRepository.save(payUser);

        String userId = "chong";
        long chargeMoney = 10000L;
        chargeService.setChargeMethod(cardCharge);

        long result = 0;

        chargeService.chargeMoney(userId, chargeMoney);

        assertEquals(result, 15000L);

        payUserRepository.findById("chong").ifPresent(payUser1->log.info(payUser1.toString()));
    }

    @Test
    void accountChargeSimultationTest(){
        Set<PayUser.ChargeMethod> chargeSet = new HashSet<>();
        chargeSet.add(PayUser.ChargeMethod.BANK_ACCOUNT);

        PayUser payUser = PayUser.builder()
                .userId("chong2").email("dmschd92@naver.com").payMoney(1500L).chargeMethods(chargeSet).build();
        payUserRepository.save(payUser);

        String userId = "chong2";
        long chargeMoney = 10000L;
        chargeService.setChargeMethod(bankCharge);

        long result = 0;
        chargeService.chargeMoney(userId, chargeMoney);

        assertEquals(result, 11500L);

        payUserRepository.findById("chong2").ifPresent(payUser1->log.info(payUser1.toString()));
    }

}



