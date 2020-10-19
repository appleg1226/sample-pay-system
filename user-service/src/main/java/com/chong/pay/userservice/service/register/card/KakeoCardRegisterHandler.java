package com.chong.pay.userservice.service.register.card;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.service.register.CardCompany;
import lombok.extern.java.Log;

@Log
public class KakeoCard implements CardCompany {
    @Override
    public boolean isValidCard(RegisterForm registerForm) {
        log.info("this KakeoCard is valid");
        return true;
    }

    @Override
    public boolean registerCard(RegisterForm registerForm) {
        log.info("KakeoCard is registered!");
        return true;
    }
}