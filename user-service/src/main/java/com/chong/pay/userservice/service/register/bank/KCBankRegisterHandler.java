package com.chong.pay.userservice.service.register.bank;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import lombok.extern.java.Log;

@Log
public class KCBankRegisterHandler implements BankRegisterHandler {
    @Override
    public boolean isValidAccount(RegisterForm registerForm) {
        log.info("this KCBank Account is valid");
        return true;
    }

    @Override
    public boolean registerAccount(RegisterForm registerForm) {
        log.info("KCBank Account is registered!");
        return true;
    }
}
