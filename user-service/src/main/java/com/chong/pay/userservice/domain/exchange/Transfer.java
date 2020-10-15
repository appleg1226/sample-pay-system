package com.chong.pay.userservice.domain.exchange;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Transfer extends Exchange {
    private String sendUser;
    private String receiveUser;
    private boolean isComplete;
}
