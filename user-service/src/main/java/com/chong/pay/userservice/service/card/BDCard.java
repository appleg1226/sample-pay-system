package com.chong.pay.userservice.service.card;

import com.chong.pay.userservice.domain.CardRegister;
import lombok.extern.java.Log;

@Log
public class BDCard implements CardCompany{
    @Override
    public boolean isValidCard(CardRegister cardRegister) {
        log.info("this BDCard is valid");
        return true;
    }

    @Override
    public boolean registerCard(CardRegister cardRegister) {
        log.info("BDCard is registered!");
        return true;
    }
}
