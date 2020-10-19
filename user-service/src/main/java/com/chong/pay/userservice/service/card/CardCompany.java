package com.chong.pay.userservice.service.card;

import com.chong.pay.userservice.domain.CardRegister;

public interface CardCompany {
    boolean isValidCard(CardRegister cardRegister);
    boolean registerCard(CardRegister cardRegister);
}
