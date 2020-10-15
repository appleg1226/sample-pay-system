package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.PayUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PayUserRepository extends ReactiveMongoRepository<PayUser, String> {
}
