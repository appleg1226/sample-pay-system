package com.chong.pay.paymentservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayUser {
    public enum ChargeMethod {
        CARD, BANK_ACCOUNT
    }
    @Id
    private String userId;
    private String email;
    private Set<ChargeMethod> chargeMethods;
    private Set<String> cardCompanyNames;
    private Set<String> bankCompanyNames;
    private long payMoney;
    private long point;
}