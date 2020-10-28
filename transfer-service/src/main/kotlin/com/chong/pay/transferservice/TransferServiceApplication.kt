package com.chong.pay.transferservice

import com.chong.pay.transferservice.domain.Exchange
import com.chong.pay.transferservice.domain.PayUser
import com.chong.pay.transferservice.repository.ExchangeRepisotory
import com.chong.pay.transferservice.repository.PayUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter




@SpringBootApplication
@EnableCaching
@EnableScheduling
class TransferServiceApplication : ApplicationRunner{

	@Autowired
	lateinit var payUserRepository: PayUserRepository

	@Autowired
	lateinit var exchangeRepository: ExchangeRepisotory

	override fun run(args: ApplicationArguments?) {
		val user1 = PayUser("eunchong", payMoney = 10000)
		val user2 = PayUser("eunwoo", payMoney = 10000)

		payUserRepository.save(user1)
		payUserRepository.save(user2)

		val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

		val exchange1 = Exchange(paymentId = "1", myId = "eunchong", otherId = "eunwoo",
				money = 1000, isComplete = false, exchangeDate = LocalDateTime.parse("2020-10-27 18:45:05", formatter))
		val exchange2 = Exchange(paymentId = "2", myId = "eunchong", otherId = "eunwoo",
				money = 1000, isComplete = false, exchangeDate = LocalDateTime.parse("2020-10-27 18:46:06", formatter))
		val exchange3 = Exchange(paymentId = "3", myId = "eunchong", otherId = "eunwoo",
				money = 1000, isComplete = false, exchangeDate = LocalDateTime.parse("2020-10-27 18:46:10", formatter))
		val exchange4 = Exchange(paymentId = "4", myId = "eunchong", otherId = "eunwoo",
				money = 1000, isComplete = false, exchangeDate = LocalDateTime.parse("2020-10-27 18:47:12", formatter))
		val exchange5 = Exchange(paymentId = "5", myId = "eunchong", otherId = "eunwoo",
				money = 1000, isComplete = false, exchangeDate = LocalDateTime.parse("2020-10-27 18:47:15", formatter))

		exchangeRepository.saveAll(listOf(exchange1, exchange2, exchange3, exchange4, exchange5))
	}
}

fun main(args: Array<String>) {
	runApplication<TransferServiceApplication>(*args)
}
