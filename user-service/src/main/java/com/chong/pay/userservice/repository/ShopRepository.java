package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.Shop;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ShopRepository extends ReactiveMongoRepository<Shop, String> {
}
