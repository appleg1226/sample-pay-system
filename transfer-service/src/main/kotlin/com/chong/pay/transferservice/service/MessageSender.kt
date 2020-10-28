package com.chong.pay.transferservice.service

import com.chong.pay.transferservice.domain.Exchange
import org.springframework.stereotype.Service

@Service
class MessageSender {

    fun sendUncompletedMessage(exchange: Exchange){
        println("보낸 사람에게 환불 알림을 전송했습니다.")
    }
}