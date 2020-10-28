package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.charge.BankRegister;
import com.chong.pay.userservice.domain.charge.CardRegister;
import com.chong.pay.userservice.domain.Exchange;
import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.repository.ExchangeRepository;
import com.chong.pay.userservice.repository.PayUserRepository;
import com.chong.pay.userservice.service.register.Connector;
import com.chong.pay.userservice.service.register.BankAccountConnector;
import com.chong.pay.userservice.service.register.CardCompanyConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Log
@RequiredArgsConstructor
public class UserInformationService {

    private final ExchangeRepository exchangeRepository;
    private final PayUserRepository payUserRepository;

    // 0. 유저 데이터 조회
    public PayUser showUserInfo(String userId){
        return payUserRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    // 1. 한 유저의 모든 거래 기록 가져오기
    @Cacheable(value = "exchange", key = "#userId")
    public List<Exchange> findAllExchanges(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 2. 한 유저의 결제 목록 가져오기
    @Cacheable(value = "exchange", key = "#userId")
    public List<Exchange> findAllPayments(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.PAYMENT))
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 3. 한 유저의 송금 목록 가져오기
    @Cacheable(value = "exchange", key = "#userId")
    public List<Exchange> findAllSends(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.SEND))
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 4. 한 유저의 송금 보낸 목록 가져오기
    @Cacheable(value = "exchange", key = "#userId")
    public List<Exchange> findAllSendsByOnlySend(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.SEND))
                .filter(exchange -> exchange.getMoney() < 0)
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 5. 한 유저의 송금 받은 목록 가져오기
    @Cacheable(value = "exchange", key = "#userId")
    public List<Exchange> findAllSendsByOnlyReceive(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.SEND))
                .filter(exchange -> exchange.getMoney() > 0)
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 6. 한 유저의 완료되지 않은 송금 목록 가져오기
    @Cacheable(value = "exchange", key = "#userId")
    public List<Exchange> findAllSendsNotCompleted(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.SEND))
                .filter(exchange -> !exchange.isComplete())
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }


    // 7. 카드 or 계좌 등록
    public boolean registerChargeMethod(String userId, RegisterForm registerForm){
        PayUser result = payUserRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        String companyName = registerForm.getCompanyName();
        Connector connector;
        PayUser.ChargeMethod type;

        if(registerForm instanceof CardRegister){
            connector = new CardCompanyConnector();
            type = PayUser.ChargeMethod.CARD;
        } else{
            connector = new BankAccountConnector();
            type = PayUser.ChargeMethod.BANK_ACCOUNT;
        }

        connector.setCompany(registerForm);

        if(connector.isValid(registerForm)){
            connector.registerCard(registerForm);
            result.getChargeMethods().add(type);
            payUserRepository.save(updateResultToDatabase(result, type, companyName));
            return true;
        } else {
            log.info("not valid!");
            return false;
        }
    }

    public static PayUser updateResultToDatabase(PayUser user, PayUser.ChargeMethod type, String companyName){
        if(type.equals(PayUser.ChargeMethod.CARD)){
            user.getChargeMethods().add(PayUser.ChargeMethod.CARD);
            user.getCardCompanyNames().add(companyName);
        } else{
            user.getChargeMethods().add(PayUser.ChargeMethod.BANK_ACCOUNT);
            user.getBankCompanyNames().add(companyName);
        }
        return user;
    }

}
