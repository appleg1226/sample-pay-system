package com.chong.pay.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class PayUser {
    public enum ChargeMethod {
        CARD, BANK_ACCOUNT
    }

    @Id
    private String userId;
    private String email;
    private Set<ChargeMethod> chargeMethods;
    private Set<CardCompanyName> cardCompanyNames;
    private long payMoney;
}
