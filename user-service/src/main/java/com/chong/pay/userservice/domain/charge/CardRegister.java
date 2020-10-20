package com.chong.pay.userservice.domain.charge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRegister implements RegisterForm{
    private String cardCompany;
    private String cardNum;
    private int yaer;
    private int month;
    private int cvc;

    @Override
    public String getCompanyName() {
        return cardCompany;
    }
}
