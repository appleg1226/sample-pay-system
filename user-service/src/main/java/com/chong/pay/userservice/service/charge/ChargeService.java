package com.chong.pay.userservice.service.charge;

import lombok.extern.java.Log;

@Log
public class ChargeService {

    private Charge charge;

    public void setChargeMethod(Charge charge){
        this.charge = charge;
    }

    public long chargeMoney(String userId, long money){
        if(charge.checkRegistered(userId)){
            return charge.charge(userId, money);
        } else{
            log.info("not registered!");
            return -1;
        }
    }
}
