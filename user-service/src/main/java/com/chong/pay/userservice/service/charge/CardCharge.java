package com.chong.pay.userservice.service.charge;

import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.service.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CardCharge implements Charge{

    private final UserInformationService userInformationService;

    @Override
    public boolean checkRegistered(String userId) {
        Set<PayUser.ChargeMethod> result = userInformationService.showUserInfo(userId).getChargeMethods();
        return result.contains(PayUser.ChargeMethod.CARD);
    }

    @Override
    public long charge(String userId, long money) {
        return userInformationService.addUsersMoney(userId, money);
    }
}
