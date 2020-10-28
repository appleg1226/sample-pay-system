package com.chong.pay.transferservice.domain

data class TransferRequest (
    var sendUserId: String = "",
    var receiveUserId: String = "",
    var money: Long = 0
)
