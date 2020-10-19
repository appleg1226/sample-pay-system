package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.charge.RegisterForm;


public interface Connector {
    void setCompany(RegisterForm registerForm);
    boolean isValid(RegisterForm registerForm);
    boolean registerCard(RegisterForm registerForm);
}
