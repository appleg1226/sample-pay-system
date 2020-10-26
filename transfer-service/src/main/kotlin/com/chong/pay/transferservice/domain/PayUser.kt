package com.chong.pay.transferservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

enum class ChargeMethod {
    CARD, BANK_ACCOUNT
}

@Document
data class PayUser (
    @Id
    var userId: String,
    var email: String? = null,
    var chargeMethods: Set<ChargeMethod>? = null,
    var cardCompanyNames: Set<String>? = null,
    var bankCompanyNames: Set<String>? = null,
    var payMoney: Long,
    var point: Long? = null
)