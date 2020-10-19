package com.chong.pay.userservice.service.register.card;

import com.chong.pay.userservice.domain.charge.RegisterForm;
import com.chong.pay.userservice.service.register.Connector;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
@Log
public class CardCompanyConnector implements Connector {

    private CardCompany cardCompany;

    public void setCompany(RegisterForm registerForm){
        switch (registerForm.getCompanyName()){
            case "BD":
                this.cardCompany = new BDCard();
                break;
            case "MASTAR":
                this.cardCompany = new MastarCard();
                break;
            case "KAKEO":
                this.cardCompany = new KakeoCard();
                break;
            case "ORI":
                this.cardCompany = new OriCard();
                break;
        }
    }

    public boolean isValid(RegisterForm registerForm){
        log.info("Validating Card... 1s");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cardCompany.isValidCard(registerForm);
    }

    public boolean registerCard(RegisterForm registerForm){
        log.info("Registering Card... 1s");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cardCompany.registerCard(registerForm);
    }
}
