package com.chong.pay.userservice.service.register.bank;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.service.register.BankCompany;
import lombok.extern.java.Log;

@Log
public class KCBank implements BankCompany {
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
