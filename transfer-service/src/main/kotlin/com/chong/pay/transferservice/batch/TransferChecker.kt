package com.chong.pay.transferservice.batch

import com.chong.pay.transferservice.controller.DefaultController
import com.chong.pay.transferservice.service.TransferHandler
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TransferChecker @Autowired constructor(val transferHandler: TransferHandler){

    companion object{
        private val log = LoggerFactory.getLogger(DefaultController::class.java)
    }

    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul")
    fun checkNotCompleted(){
        val nowDateTime = LocalDateTime.now()
        log.info("now time: $nowDateTime")
        transferHandler.cancelNotCompleted(nowDateTime)
    }
}