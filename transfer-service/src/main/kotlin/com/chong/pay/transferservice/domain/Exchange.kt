package com.chong.pay.transferservice.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

enum class EXCHANGE_TYPE {
    PAYMENT, SEND
}

@Document
data class Exchange(
        @Id
        var paymentId: String = "",
        var exchangeType: EXCHANGE_TYPE? = null,
        var myId: String = "",
        var otherId: String = "",
        var money: Long = 0,
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        var exchangeDate: LocalDateTime? = null,
        var isComplete: Boolean = false
)