package com.chong.pay.transferservice.service

import com.chong.pay.transferservice.domain.PayUser
import com.chong.pay.transferservice.domain.TransferRequest
import com.chong.pay.transferservice.repository.ExchangeRepisotory
import com.chong.pay.transferservice.repository.PayUserRepository
import org.junit.jupiter.api.Test

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
internal class TransferHandlerTest {

    @Autowired
    lateinit var transferHandler: TransferHandler

    @Autowired
    lateinit var userRepository: PayUserRepository

    @Autowired
    lateinit var exchangeRepisotory: ExchangeRepisotory

    companion object{
        private val log = LoggerFactory.getLogger(TransferHandler::class.java)
    }

    @Test
    fun transferMoneyRequest() {
//        val user1 = PayUser(userId = "h1", payMoney = 10000)
//        val user2 = PayUser(userId = "h2", payMoney = 10000)
//        userRepository.saveAll(listOf(user1, user2))
//
//        val request = TransferRequest("h1", "h2", 5000)
//
//        val result = transferHandler.transferMoneyRequest(request)
//
//        val user1Mid = userRepository.findByIdOrNull("h1") ?: NoSuchElementException("")
//        log.info(user1Mid.toString())
//        val user2Mid = userRepository.findByIdOrNull("h2") ?: NoSuchElementException("")
//        log.info(user2Mid.toString())
//
//        transferHandler.decideTransfer(result.paymentId)
//
//        val user1Final = userRepository.findByIdOrNull("h1") ?: NoSuchElementException("")
//        log.info(user1Final.toString())
//        val user2Final = userRepository.findByIdOrNull("h2") ?: NoSuchElementException("")
//        log.info(user2Final.toString())
    }

    @Test
    fun decideTransfer() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val nowDateTime = LocalDateTime.parse("2020-10-28 18:37", formatter)

        val date1: LocalDateTime = LocalDateTime.of(nowDateTime.year, nowDateTime.month, nowDateTime.dayOfMonth - 1,
                nowDateTime.hour, nowDateTime.minute, 0)
        val date2: LocalDateTime = LocalDateTime.of(nowDateTime.year, nowDateTime.month, nowDateTime.dayOfMonth - 1,
                nowDateTime.hour, nowDateTime.minute + 1, 0)

        println(date1)
        println(date2)

        var result = exchangeRepisotory.findAllByExchangeDateBetweenAndIsCompleteFalse(date1, date2)
        val result2 = exchangeRepisotory.findAll()

        result.forEach { exchange -> println(exchange.toString()) }
        result2.forEach { exchange -> println(exchange.toString()) }
    }
}