package com.chong.pay.userservice.domain.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Exchange {
    @Id
    private String exchangeId;
    private long money;
    private LocalDateTime exchangeDate;
}
