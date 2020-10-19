package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.CardRegister;
import com.chong.pay.userservice.domain.Exchange;
import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.repository.ExchangeRepository;
import com.chong.pay.userservice.repository.PayUserRepository;
import com.chong.pay.userservice.service.card.CardCompanyConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
    private final CardCompanyConnector cardConnector;

    // 0. 유저 데이터 조회
    public PayUser showUserInfo(String userId){
        return payUserRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    // 1. 한 유저의 모든 거래 기록 가져오기
    public List<Exchange> findAllExchanges(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 2. 한 유저의 결제 목록 가져오기
    public List<Exchange> findAllPayments(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.PAYMENT))
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 3. 한 유저의 송금 목록 가져오기
    public List<Exchange> findAllSends(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.SEND))
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 4. 한 유저의 송금 보낸 목록 가져오기
    public List<Exchange> findAllSendsByOnlySend(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.SEND))
                .filter(exchange -> exchange.getMoney() < 0)
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 5. 한 유저의 송금 받은 목록 가져오기
    public List<Exchange> findAllSendsByOnlyReceive(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.SEND))
                .filter(exchange -> exchange.getMoney() > 0)
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 6. 한 유저의 완료되지 않은 송금 목록 가져오기
    public List<Exchange> findAllSendsNotCompleted(String userId){
        return exchangeRepository.findAllByMyId(userId)
                .stream()
                .filter(exchange -> exchange.getExchangeType().equals(Exchange.EXCHANGE_TYPE.SEND))
                .filter(exchange -> !exchange.isComplete())
                .sorted(Comparator.comparing(Exchange::getExchangeDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    // 7. 유저 잔액 변경
    public long addUsersMoney(String userId, long money){
        PayUser result = payUserRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        result.setPayMoney(result.getPayMoney() + money);
        return payUserRepository.save(result).getPayMoney();
    }

    // 8. Card 등록
    public void registerCard(String userId, CardRegister cardRegister){
        PayUser result = payUserRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        // 해당 카드회사 객체 등록
        cardConnector.setCardCompany(cardRegister);

        // 카드 등록 가능 여부 확인
        cardConnector.isValid(cardRegister);

        // 카드회사에 카드 등록 요청
        cardConnector.registerCard(cardRegister);

        // 카드 등록 정보 업데이트
        result.getChargeMethods().add(PayUser.ChargeMethod.CARD);
        result.getCardCompanyNames().add(cardRegister.getCardCompany());
        payUserRepository.save(result);
    }

    // 9. 계좌 등록
    public void registerAccount(){

    }

}
