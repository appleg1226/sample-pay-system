package com.chong.pay.userservice.service.charge;

public interface Charge {
    boolean checkRegistered(String userId);
    long charge(String userId, long money);
}
