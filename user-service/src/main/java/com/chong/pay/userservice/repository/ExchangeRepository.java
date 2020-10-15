package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.exchange.Exchange;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ExchangeRepository extends ReactiveMongoRepository<Exchange, String> {
}
