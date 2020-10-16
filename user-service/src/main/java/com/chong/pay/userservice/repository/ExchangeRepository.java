package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.Exchange;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ExchangeRepository extends ReactiveMongoRepository<Exchange, Long> {
    Flux<Exchange> findAllByMyId(String myId);
}
