package com.chong.pay.userservice.controller;

import com.chong.pay.userservice.domain.Exchange;
import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.domain.Shop;
import com.chong.pay.userservice.domain.charge.BankRegister;
import com.chong.pay.userservice.domain.charge.CardRegister;
import com.chong.pay.userservice.repository.PayUserRepository;
import com.chong.pay.userservice.service.ShopInformationService;
import com.chong.pay.userservice.service.UserInformationService;
import com.chong.pay.userservice.service.charge.BankCharge;
import com.chong.pay.userservice.service.charge.CardCharge;
import com.chong.pay.userservice.service.charge.ChargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
@RequiredArgsConstructor
public class DefaultController {

    private final ShopInformationService shopInformationService;
    private final UserInformationService userInformationService;
    private final PayUserRepository payUserRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<PayUser> showUserInfo(@PathVariable("id") String userId){
        return new ResponseEntity<>(userInformationService.showUserInfo(userId), HttpStatus.OK);
    }

    @GetMapping("/user/exchanges/all/{id}")
    public ResponseEntity<List<Exchange>> showUserExchangesList(@PathVariable("id") String userId){
        return new ResponseEntity<>(userInformationService.findAllExchanges(userId), HttpStatus.OK);
    }

    @GetMapping("/user/exchanges/payment/{id}")
    public ResponseEntity<List<Exchange>> showUserPaymentsList(@PathVariable("id") String userId){
        return new ResponseEntity<>(userInformationService.findAllPayments(userId), HttpStatus.OK);
    }

    @GetMapping("/user/exchanges/send/{id}")
    public ResponseEntity<List<Exchange>> showUserSendsList(@PathVariable("id") String userId){
        return new ResponseEntity<>(userInformationService.findAllSends(userId), HttpStatus.OK);
    }

    @GetMapping("/user/exchanges/send/to/{id}")
    public ResponseEntity<List<Exchange>> showUserOnlySendList(@PathVariable("id") String userId){
        return new ResponseEntity<>(userInformationService.findAllSendsByOnlySend(userId), HttpStatus.OK);
    }

    @GetMapping("/user/exchanges/send/from/{id}")
    public ResponseEntity<List<Exchange>> showUserOnlyReceiveList(@PathVariable("id") String userId){
        return new ResponseEntity<>(userInformationService.findAllSendsByOnlyReceive(userId), HttpStatus.OK);
    }

    @GetMapping("/user/exchanges/send/not-completed/{id}")
    public ResponseEntity<List<Exchange>> showUserSendNotCompletedList(@PathVariable("id") String userId){
        return new ResponseEntity<>(userInformationService.findAllSendsNotCompleted(userId), HttpStatus.OK);
    }

    @PostMapping("/user/register/card/{id}")
    public ResponseEntity<String> registerCard(@PathVariable("id") String userId, @RequestBody CardRegister cardRegister){
        boolean result = userInformationService.registerChargeMethod(userId, cardRegister);

        if(result){
            return new ResponseEntity<>("register success", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("register failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/register/bank/{id}")
    public ResponseEntity<String> registerBankAccount(@PathVariable("id") String userId, @RequestBody BankRegister bankRegister){
        boolean result = userInformationService.registerChargeMethod(userId, bankRegister);

        if(result){
            return new ResponseEntity<>("register success", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("register failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/charge/card/{id}")
    public ResponseEntity<String> chargeMoneyByCard(@PathVariable("id") String userId, @RequestBody long money){
        ChargeService chargeService = new ChargeService();
        chargeService.setChargeMethod(new CardCharge(payUserRepository));
        long result = chargeService.chargeMoney(userId, money);

        if(result == -1){
            return new ResponseEntity<>("charge failed", HttpStatus.BAD_REQUEST);
        } else{
            return new ResponseEntity<>("charge success", HttpStatus.OK);
        }
    }

    @PostMapping("/user/charge/bank/{id}")
    public ResponseEntity<String> chargeMoneyByBank(@PathVariable("id") String userId, @RequestBody long money){
        ChargeService chargeService = new ChargeService();
        chargeService.setChargeMethod(new BankCharge(payUserRepository));
        long result = chargeService.chargeMoney(userId, money);

        if(result == -1){
            return new ResponseEntity<>("charge failed", HttpStatus.BAD_REQUEST);
        } else{
            return new ResponseEntity<>("charge success", HttpStatus.OK);
        }
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<Shop> showShopInfo(@PathVariable("id") String shopId){
        return new ResponseEntity<>(shopInformationService.showShopInfo(shopId), HttpStatus.OK);
    }
}
