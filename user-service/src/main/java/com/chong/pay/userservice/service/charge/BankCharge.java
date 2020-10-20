package com.chong.pay.userservice.service.charge;

import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.repository.PayUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Log
public class BankCharge implements Charge {

    private final PayUserRepository payUserRepository;

    @Override
    public boolean checkRegistered(String userId) {
        Set<PayUser.ChargeMethod> result = payUserRepository.findById(userId).orElseThrow(NoSuchElementException::new).getChargeMethods();
        return result.contains(PayUser.ChargeMethod.BANK_ACCOUNT);
    }

    @Override
    public long charge(String userId, long money) {
        log.info("Charging by Bank Account...");
        PayUser result = payUserRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        result.setPayMoney(result.getPayMoney() + money);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Charging Completed");
        return payUserRepository.save(result).getPayMoney();
    }
}
