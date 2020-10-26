package com.chong.pay.paymentservice.service;

import com.chong.pay.paymentservice.domain.PayUser;
import com.chong.pay.paymentservice.repository.PayUserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Log
public class PointValidator {

    @Autowired
    PayUserRepository payUserRepository;

    public boolean validateUserPoint(String userId, int point){
        PayUser result = payUserRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return result.getPoint() > point;
    }
}
