package com.chong.pay.transferservice.repository

import com.chong.pay.transferservice.domain.Exchange
import org.springframework.data.mongodb.repository.MongoRepository

interface ExchangeRepisotory : MongoRepository<Exchange, String>