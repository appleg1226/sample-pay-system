package com.chong.pay.userservice.service.register;

import com.chong.pay.userservice.domain.charge.RegisterForm;

public interface CardCompany {
    boolean isValidCard(RegisterForm registerForm);
    boolean registerCard(RegisterForm registerForm);
}
