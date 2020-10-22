package com.chong.pay.paymentservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String userId;
    private String shopName;
    private int money;
}
