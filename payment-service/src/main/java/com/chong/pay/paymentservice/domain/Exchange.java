package com.chong.pay.paymentservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Exchange {
    public enum EXCHANGE_TYPE{
        PAYMENT, SEND
    }
    @Id
    private String paymentId;
    private EXCHANGE_TYPE exchangeType;
    private String myId;
    private String otherId;
    private long money;     // 음수이면 송금, 양수이면 받음
    private LocalDateTime exchangeDate;
    private boolean isComplete;
}
