package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.Exchange;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExchangeRepository extends MongoRepository<Exchange, String> {
    List<Exchange> findAllByMyId(String myId);
}
