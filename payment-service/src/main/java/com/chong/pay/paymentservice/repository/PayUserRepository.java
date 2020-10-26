package com.chong.pay.paymentservice.repository;

import com.chong.pay.paymentservice.domain.PayUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PayUserRepository extends MongoRepository<PayUser, String> {
}
