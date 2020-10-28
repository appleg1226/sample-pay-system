package com.chong.pay.transferservice.repository

import com.chong.pay.transferservice.domain.Exchange
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface ExchangeRepisotory : MongoRepository<Exchange, String>{
    fun findAllByExchangeDateBetweenAndIsCompleteFalse(start: LocalDateTime, end: LocalDateTime): List<Exchange>
}