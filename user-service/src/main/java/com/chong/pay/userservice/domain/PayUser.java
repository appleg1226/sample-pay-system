package com.chong.pay.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class PayUser {
    @Id
    private String userId;
    private String email;
    private Map<ChargeMethod, String> chargeMethods;
    private long payMoney;
}
