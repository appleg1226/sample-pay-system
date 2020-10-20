package com.chong.pay.userservice.service.register.bank;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import lombok.extern.java.Log;

@Log
public class HelloBankRegisterHandler implements BankRegisterHandler {
    @Override
    public boolean isValidAccount(RegisterForm registerForm) {
        log.info("this HelloBank Account is valid");
        return true;
    }

    @Override
    public boolean registerAccount(RegisterForm registerForm) {
        log.info("HelloBank Account is registered!");
        return true;
    }
}
