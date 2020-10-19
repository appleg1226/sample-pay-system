package com.chong.pay.userservice.service.register.card;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.service.register.CardCompany;
import lombok.extern.java.Log;

@Log
public class MastarCard implements CardCompany {
    @Override
    public boolean isValidCard(RegisterForm registerForm) {
        log.info("this MastarCard is valid");
        return true;
    }

    @Override
    public boolean registerCard(RegisterForm registerForm) {
        log.info("MastarCard is registered!");
        return true;
    }
}