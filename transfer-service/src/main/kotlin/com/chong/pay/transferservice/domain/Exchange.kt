package com.chong.pay.transferservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

enum class EXCHANGE_TYPE {
    PAYMENT, SEND
}

@Document
data class Exchange (
    @Id
    var paymentId: String = "",
    var exchangeType: EXCHANGE_TYPE? = null,
    var myId: String = "",
    var otherId: String = "",
    var money: Long = 0,
    var exchangeDate: LocalDateTime? = null,
    var isComplete: Boolean = false
)