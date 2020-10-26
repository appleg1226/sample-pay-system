package com.chong.pay.transferservice.service

import com.chong.pay.transferservice.domain.EXCHANGE_TYPE
import com.chong.pay.transferservice.domain.Exchange
import com.chong.pay.transferservice.domain.TransferRequest
import com.chong.pay.transferservice.repository.ExchangeRepisotory
import com.chong.pay.transferservice.repository.PayUserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.NoSuchElementException

@Service
class TransferHandler @Autowired constructor(val exchangeRepository: ExchangeRepisotory, val payUserRepository: PayUserRepository){

    companion object{
        private val log = LoggerFactory.getLogger(TransferHandler::class.java)
    }

    fun transferMoneyRequest(transferRequest: TransferRequest): Exchange {
        val exchage = Exchange(paymentId = exchangeIdGenerator(LocalDateTime.now()),
                exchangeType = EXCHANGE_TYPE.SEND,
                myId = transferRequest.sendUserId,
                otherId = transferRequest.receiveUserId,
                money = transferRequest.money,
                isComplete = false,
                exchangeDate = LocalDateTime.now())

        saveExchangeRecord(exchage)
        updateSendUser(transferRequest.sendUserId, transferRequest.money)
        log.info("송금이 정상 요청 되었습니다. 상대방이 24시간 내에 수락하면 송금이 완료됩니다.")
        return exchage
    }

    fun decideTransfer(paymentId: String){
        val exchange = exchangeRepository.findByIdOrNull(paymentId) ?: throw NoSuchElementException("Not Found Exchange")
        updateExchangeComplete(exchange)
        updateReceiveUser(exchange.otherId, exchange.money)
        log.info("송금이 전부 완료 되었습니다.")
    }

    private fun saveExchangeRecord(exchange: Exchange){
        exchangeRepository.save(exchange)
        log.info("saved exchange: " + exchange.paymentId)
    }

    private fun exchangeIdGenerator(dateTime: LocalDateTime): String {
        return dateTime.year.toString() +
                dateTime.monthValue +
                dateTime.dayOfMonth +
                UUID.randomUUID().toString().substring(0, 7)
    }

    private fun updateExchangeComplete(exchange: Exchange){
        exchange.isComplete = true
        log.info("거래가 정상 완료 되었습니다.")
    }

    private fun updateSendUser(userId: String, money: Long){
        val userInfo = payUserRepository.findByIdOrNull(userId) ?: throw NoSuchElementException("Not Found User")
        userInfo.payMoney -= money
        payUserRepository.save(userInfo)
        log.info("송금 요청: 금액이 계좌에서 빠져나감")
    }

    private fun updateReceiveUser(userId: String, money: Long){
        val userInfo = payUserRepository.findByIdOrNull(userId) ?: throw NoSuchElementException("Not Found User")
        userInfo.payMoney += money
        payUserRepository.save(userInfo)
        log.info("송금 완료: 금액이 계좌로 들어옴")
    }
}