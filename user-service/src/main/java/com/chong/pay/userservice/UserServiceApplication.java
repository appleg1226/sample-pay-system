package com.chong.pay.userservice;

import com.chong.pay.userservice.domain.Exchange;
import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.repository.ExchangeRepository;
import com.chong.pay.userservice.repository.PayUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
@EnableCaching
public class UserServiceApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Autowired
	ExchangeRepository exchangeRepository;

	@Autowired
	PayUserRepository payUserRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		PayUser payUser = PayUser.builder()
				.userId("chong").email("dmschd92@naver.com").payMoney(5000L)
				.chargeMethods(new HashSet<>())
				.cardCompanyNames(new HashSet<>())
				.bankCompanyNames(new HashSet<>())
				.build();
		payUserRepository.save(payUser);
		System.out.println("hi");

		Exchange exchange1 = Exchange.builder()
				.paymentId("1").myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
				.money(10000L).exchangeDate(LocalDateTime.of(2020, 1, 1, 0, 0)).isComplete(false).build();
		Exchange exchange2 = Exchange.builder()
				.paymentId("2").myId("chong").otherId("kakao").exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
				.money(9000L).exchangeDate(LocalDateTime.of(2020, 3, 1, 0, 0)).build();
		Exchange exchange3 = Exchange.builder()
				.paymentId("3").myId("chong").otherId("kakao").exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
				.money(9000L).exchangeDate(LocalDateTime.of(2020, 5, 1, 0, 0)).build();
		Exchange exchange4 = Exchange.builder()
				.paymentId("4").myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
				.money(-3000L).exchangeDate(LocalDateTime.of(2020, 2, 1, 0, 0)).isComplete(true).build();
		Exchange exchange5 = Exchange.builder()
				.paymentId("5").myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
				.money(6000L).exchangeDate(LocalDateTime.of(2020, 4, 1, 0, 0)).isComplete(false).build();

		exchangeRepository.saveAll(Arrays.asList(exchange2, exchange1, exchange3, exchange4, exchange5));

	}
}
