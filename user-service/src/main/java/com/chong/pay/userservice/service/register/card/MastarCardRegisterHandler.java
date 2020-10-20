package com.chong.pay.userservice.service.register.card;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import lombok.extern.java.Log;

@Log
public class MastarCardRegisterHandler implements CardRegisterHandler {
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