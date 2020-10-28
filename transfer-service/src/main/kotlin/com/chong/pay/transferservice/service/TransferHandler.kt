package com.chong.pay.transferservice.service

import com.chong.pay.transferservice.domain.EXCHANGE_TYPE
import com.chong.pay.transferservice.domain.Exchange
import com.chong.pay.transferservice.domain.TransferRequest
import com.chong.pay.transferservice.repository.ExchangeRepisotory
import com.chong.pay.transferservice.repository.PayUserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.NoSuchElementException

@Service
class TransferHandler @Autowired constructor(val exchangeRepository: ExchangeRepisotory,
                                             val payUserRepository: PayUserRepository,
                                             val messageSender: MessageSender){

    companion object{
        private val log = LoggerFactory.getLogger(TransferHandler::class.java)
    }

    @Cacheable(value = ["transfer"], key = "#exchangeId")
    fun transferMoneyRequest(transferRequest: TransferRequest, exchangeId: String): Exchange {
        val exchange = Exchange(paymentId = exchangeId,
                exchangeType = EXCHANGE_TYPE.SEND,
                myId = transferRequest.sendUserId,
                otherId = transferRequest.receiveUserId,
                money = transferRequest.money,
                isComplete = false,
                exchangeDate = LocalDateTime.now())

        val result = saveExchangeRecord(exchange)
        log.info(result.toString())
        updateSendUser(transferRequest.sendUserId, transferRequest.money)

        // 보내는 사람에게 알람 보내기

        log.info("송금이 정상 요청 되었습니다. 상대방이 24시간 내에 수락하면 송금이 완료됩니다.")
        return exchange
    }

    fun decideTransfer(paymentId: String){
        val exchange = exchangeRepository.findByIdOrNull(paymentId) ?: throw NoSuchElementException("Not Found Exchange")
        updateExchangeComplete(exchange)
        updateReceiveUser(exchange.otherId, exchange.money)

        log.info("송금이 전부 완료 되었습니다.")
    }

    fun cancelNotCompleted(nowDateTime: LocalDateTime){
        // 1. 하루가 지났지만 현재 끝나지 않은 송금 찾기
        val resultList = findNotCompleted(nowDateTime)

        resultList.map { exchange -> println(exchange.toString()) }

        // 2. 보낸 사람에게 돈 돌려 보내기
        resultList.map {exchange -> refundOne(exchange)}

        // 3. 보낸 사람들에게 문자 보내기
        resultList.map { exchange -> messageSender.sendUncompletedMessage(exchange) }
    }

    private fun saveExchangeRecord(exchange: Exchange): Exchange{
        val exchangeId = exchange.paymentId
        val result = exchangeRepository.save(exchange)
        log.info("saved exchange: $exchangeId")
        return result
    }


    private fun updateExchangeComplete(exchange: Exchange){
        exchange.isComplete = true
        exchangeRepository.save(exchange)
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

    private fun findNotCompleted(nowDateTime: LocalDateTime) : List<Exchange> {
        val date1: LocalDateTime = LocalDateTime.of(nowDateTime.year, nowDateTime.month, nowDateTime.dayOfMonth - 1,
                nowDateTime.hour, nowDateTime.minute, 0)
        val date2: LocalDateTime = LocalDateTime.of(nowDateTime.year, nowDateTime.month, nowDateTime.dayOfMonth - 1,
                nowDateTime.hour, nowDateTime.minute + 1, 0)

        log.info(date1.toString())
        log.info(date2.toString())
        return exchangeRepository.findAllByExchangeDateBetweenAndIsCompleteFalse(date1, date2)
    }

    private fun refundOne(exchange: Exchange){
        val result = payUserRepository.findByIdOrNull(exchange.myId) ?: throw NoSuchElementException("Not Found User")
        result.payMoney += exchange.money
        payUserRepository.save(result)
        log.info(result.userId + "의 계정에 돈을 환불했습니다.")
    }


}