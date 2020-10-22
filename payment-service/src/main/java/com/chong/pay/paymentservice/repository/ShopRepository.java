package com.chong.pay.userservice.repository;

import com.chong.pay.userservice.domain.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, String> {
}
