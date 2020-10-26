package com.chong.pay.transferservice.domain

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class TransferRequest (
    var sendUserId: String = "",
    var receiveUserId: String = "",
    var money: Long = 0,

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var dateTime: LocalDateTime? = null
)
