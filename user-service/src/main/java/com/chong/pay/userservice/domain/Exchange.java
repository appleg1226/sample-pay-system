package com.chong.pay.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Exchange {
    public enum EXCHANGE_TYPE{
        PAYMENT, SEND
    }

    @Id
    private long paymentId;
    private EXCHANGE_TYPE exchangeType;
    private String myId;
    private String otherId;
    private long money;     // 음수이면 송금, 양수이면 받음
    private LocalDateTime exchangeDate;
    private boolean isComplete;
}
