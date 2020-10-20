package com.chong.pay.userservice.service.register;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.service.register.bank.BankRegisterHandler;
import com.chong.pay.userservice.service.register.bank.HelloBankRegisterHandler;
import com.chong.pay.userservice.service.register.bank.KCBankRegisterHandler;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
@Log
public class BankAccountConnector implements Connector {
    private BankRegisterHandler bankRegisterHandler;

    public void setCompany(RegisterForm registerForm){
        switch (registerForm.getCompanyName()){
            case "KC":
                this.bankRegisterHandler = new KCBankRegisterHandler();
                break;
            case "HELLO":
                this.bankRegisterHandler = new HelloBankRegisterHandler();
                break;
        }
    }

    public boolean isValid(RegisterForm registerForm){
        log.info("Validating Account... 1s");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bankRegisterHandler.isValidAccount(registerForm);
    }

    public boolean registerCard(RegisterForm registerForm){
        log.info("Registering Account... 1s");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bankRegisterHandler.registerAccount(registerForm);
    }
}
