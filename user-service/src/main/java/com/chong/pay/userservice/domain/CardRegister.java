package com.chong.pay.userservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardRegister {
    private CardCompanyName cardCompany;
    private String cardNum;
    private int yaer;
    private int month;
    private int cvc;
}
