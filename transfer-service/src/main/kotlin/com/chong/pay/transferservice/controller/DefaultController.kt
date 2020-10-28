package com.chong.pay.transferservice.controller

import com.chong.pay.transferservice.domain.Exchange
import com.chong.pay.transferservice.domain.TransferRequest
import com.chong.pay.transferservice.service.TransferHandler
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*

@RestController
class DefaultController @Autowired constructor(val transferHandler: TransferHandler){

    companion object{
        private val log = LoggerFactory.getLogger(DefaultController::class.java)
    }

    @PostMapping("/transfer/process")
    fun transferRequest(@RequestBody transferRequest: TransferRequest): ResponseEntity<Exchange>{

        val exchangeId = exchangeIdGenerator(LocalDateTime.now())

        val result = transferHandler.transferMoneyRequest(transferRequest, exchangeId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PostMapping("/transfer/complete/{pay-id}")
    fun decideTransfer(@PathVariable("pay-id") paymentId: String): ResponseEntity<String>{
        transferHandler.decideTransfer(paymentId)
        return ResponseEntity("complete", HttpStatus.OK)
    }

    private fun exchangeIdGenerator(dateTime: LocalDateTime): String {
        return dateTime.year.toString() +
                dateTime.monthValue +
                dateTime.dayOfMonth +
                UUID.randomUUID().toString().substring(0, 7)
    }
}