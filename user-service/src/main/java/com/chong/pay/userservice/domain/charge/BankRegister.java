package com.chong.pay.userservice.domain.charge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankRegister implements RegisterForm{
    private String bankName;
    private String accountNo;

    @Override
    public String getCompanyName() {
        return bankName;
    }
}
