package com.chong.pay.userservice.service.register;

import com.chong.pay.userservice.domain.charge.RegisterForm;

public interface BankRegisterHandler {
    boolean isValidAccount(RegisterForm registerForm);
    boolean registerAccount(RegisterForm registerForm);
}
