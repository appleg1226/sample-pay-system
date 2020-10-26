package com.chong.pay.transferservice.service

import com.chong.pay.transferservice.domain.PayUser
import com.chong.pay.transferservice.domain.TransferRequest
import com.chong.pay.transferservice.repository.PayUserRepository
import org.junit.jupiter.api.Test

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@SpringBootTest
internal class TransferHandlerTest {

    @Autowired
    lateinit var transferHandler: TransferHandler

    @Autowired
    lateinit var userRepository: PayUserRepository

    companion object{
        private val log = LoggerFactory.getLogger(TransferHandler::class.java)
    }

    @Test
    fun transferMoneyRequest() {
        val user1 = PayUser(userId = "h1", payMoney = 10000)
        val user2 = PayUser(userId = "h2", payMoney = 10000)
        userRepository.saveAll(listOf(user1, user2))

        val request = TransferRequest("h1", "h2", 5000, LocalDateTime.now())

        val result = transferHandler.transferMoneyRequest(request)

        val user1Mid = userRepository.findByIdOrNull("h1") ?: NoSuchElementException("")
        log.info(user1Mid.toString())
        val user2Mid = userRepository.findByIdOrNull("h2") ?: NoSuchElementException("")
        log.info(user2Mid.toString())

        transferHandler.decideTransfer(result.paymentId)

        val user1Final = userRepository.findByIdOrNull("h1") ?: NoSuchElementException("")
        log.info(user1Final.toString())
        val user2Final = userRepository.findByIdOrNull("h2") ?: NoSuchElementException("")
        log.info(user2Final.toString())
    }

    @Test
    fun decideTransfer() {

    }
}