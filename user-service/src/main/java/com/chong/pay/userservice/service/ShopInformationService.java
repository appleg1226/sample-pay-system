package com.chong.pay.userservice.service;

import com.chong.pay.userservice.domain.Shop;
import com.chong.pay.userservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Log
@RequiredArgsConstructor
public class ShopInformationService {

    private final ShopRepository shopRepository;

    public Shop showShopInfo(String shopId){
        return shopRepository.findById(shopId).orElseThrow(NoSuchElementException::new);
    }

}
