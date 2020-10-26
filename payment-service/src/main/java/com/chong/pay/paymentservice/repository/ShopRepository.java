package com.chong.pay.paymentservice.repository;

import com.chong.pay.paymentservice.domain.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, String> {
}
