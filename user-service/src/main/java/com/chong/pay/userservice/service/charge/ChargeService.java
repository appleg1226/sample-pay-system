package com.chong.pay.userservice.service.charge;

import org.springframework.stereotype.Service;

@Service
public class ChargeService {

    private Charge charge;

    public void setChargeMethod(Charge charge){
        this.charge = charge;
    }

    public boolean isRegistered(String userId){
        return charge.checkRegistered(userId);
    }

    public long chargeMoney(String userId, long money){
        return charge.charge(userId, money);
    }
}
