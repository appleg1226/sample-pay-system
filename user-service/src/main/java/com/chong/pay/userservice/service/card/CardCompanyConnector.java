package com.chong.pay.userservice.service.card;

import com.chong.pay.userservice.domain.CardRegister;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
@Log
public class CardCompanyConnector {

    private CardCompany cardCompany;

    public void setCardCompany(CardRegister cardRegister){
        switch (cardRegister.getCardCompany()){
            case BD:
                this.cardCompany = new BDCard();
                break;
            case MASTAR:
                this.cardCompany = new MastarCard();
                break;
            case KAKEO:
                this.cardCompany = new KakeoCard();
                break;
            case ORI:
                this.cardCompany = new OriCard();
                break;
        }
    }

    public boolean isValid(CardRegister cardRegister){
        log.info("Validating Card... 1s");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cardCompany.isValidCard(cardRegister);
    }

    public boolean registerCard(CardRegister cardRegister){
        log.info("Registering Card... 1s");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cardCompany.registerCard(cardRegister);
    }
}
