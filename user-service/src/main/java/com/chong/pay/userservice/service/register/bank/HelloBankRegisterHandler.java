package com.chong.pay.userservice.service.register.bank;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.service.register.BankCompany;
import lombok.extern.java.Log;

@Log
public class HelloBank implements BankCompany {
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
