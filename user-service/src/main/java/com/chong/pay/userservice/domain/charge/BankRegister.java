package com.chong.pay.userservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankRegister {
    private BankName bankName;
    private String accountNo;
}
