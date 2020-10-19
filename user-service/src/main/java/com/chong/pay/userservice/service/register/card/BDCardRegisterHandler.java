package com.chong.pay.userservice.service.register.card;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.service.register.CardCompany;
import lombok.extern.java.Log;

@Log
public class BDCard implements CardCompany {
    @Override
    public boolean isValidCard(RegisterForm registerForm) {
        log.info("this BDCard is valid");
        return true;
    }

    @Override
    public boolean registerCard(RegisterForm registerForm) {
        log.info("BDCard is registered!");
        return true;
    }
}
