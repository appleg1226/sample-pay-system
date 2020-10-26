package com.chong.pay.paymentservice.repository;

import com.chong.pay.paymentservice.domain.Exchange;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExchangeRepository extends MongoRepository<Exchange, String> {
}
