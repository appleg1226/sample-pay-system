package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.exchange.Exchange;
import com.chong.pay.userservice.repository.PayUserRepository;
import com.chong.pay.userservice.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Log
@RequiredArgsConstructor
public class UserInformationService {

    private final PayUserRepository userRepository;
    private final ExchangeRepository exchangeRepository;

    public Flux<Exchange> showAllExchange(String userId){


        return null;
    }
}
