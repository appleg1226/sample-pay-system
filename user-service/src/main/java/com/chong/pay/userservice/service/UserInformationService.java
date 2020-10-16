package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.Exchange;
import com.chong.pay.userservice.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Service
@Log
@RequiredArgsConstructor
public class UserInformationService {

    @Autowired
    ExchangeRepository exchangeRepository;

    // 1. 한 유저의 모든 거래 기록 가져오기
    public Flux<Exchange> findAllExchangeOfOne(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .sort(Comparator.comparing(Exchange::getExchangeDate));
    }

    // 2. 한 유저의 결제 목록 가져오기
    // 3. 한 유저의 송금 목록 가져오기


}
