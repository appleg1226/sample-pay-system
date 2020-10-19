package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.PayUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PayUserRepository extends MongoRepository<PayUser, String> {
}
