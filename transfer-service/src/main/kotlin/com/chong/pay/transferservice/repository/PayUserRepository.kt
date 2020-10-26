package com.chong.pay.transferservice.repository

import com.chong.pay.transferservice.domain.PayUser
import org.springframework.data.mongodb.repository.MongoRepository

interface PayUserRepository: MongoRepository<PayUser, String>