package com.chong.pay.userservice.service.register.bank;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.service.register.Connector;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
@Log
public class BankAccountConnector implements Connector {
    private BankCompany bankCompany;

    public void setCompany(RegisterForm registerForm){
        switch (registerForm.getCompanyName()){
            case "KC":
                this.bankCompany = new KCBank();
                break;
            case "HELLO":
                this.bankCompany = new HelloBank();
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
        return bankCompany.isValidAccount(registerForm);
    }

    public boolean registerCard(RegisterForm registerForm){
        log.info("Registering Account... 1s");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bankCompany.registerAccount(registerForm);
    }
}
