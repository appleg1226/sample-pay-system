package com.chong.pay.userservice.service.register.card;

import com.chong.pay.userservice.domain.charge.RegisterForm;

public interface CardRegisterHandler {
    boolean isValidCard(RegisterForm registerForm);
    boolean registerCard(RegisterForm registerForm);
}
