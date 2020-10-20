package com.chong.pay.userservice.controller;

import com.chong.pay.userservice.domain.Exchange;
import com.chong.pay.userservice.domain.PayUser;
import com.chong.pay.userservice.domain.Shop;
import com.chong.pay.userservice.domain.charge.BankRegister;
import com.chong.pay.userservice.domain.charge.CardRegister;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DefaultController.class)
class DefaultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DefaultController defaultController;

    private PayUser payUser;
    private Exchange e1;
    private Exchange e2;
    private Exchange e3;
    private Exchange e4;
    private Exchange e5;

    @BeforeEach
    void setup(){
        Set<PayUser.ChargeMethod> methodSet = new HashSet<>(Arrays.asList(PayUser.ChargeMethod.CARD, PayUser.ChargeMethod.BANK_ACCOUNT));
        Set<String> cardCompanies = new HashSet<>(Arrays.asList("BD", "MASTAR", "KAKEO", "ORI"));
        Set<String> bankCompanies = new HashSet<>(Arrays.asList("HELLO", "KC"));

        payUser = PayUser.builder()
                .userId("chong").email("dmschd92@naver.com").payMoney(5000L)
                .chargeMethods(methodSet)
                .cardCompanyNames(cardCompanies)
                .bankCompanyNames(bankCompanies)
                .build();

        e1 = Exchange.builder()
                .paymentId(1L).myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
                .money(10000L).exchangeDate(LocalDateTime.of(2020, 1, 1, 0, 0)).isComplete(false).build();
        e2 = Exchange.builder()
                .paymentId(2L).myId("chong").otherId("kakao").exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
                .money(9000L).exchangeDate(LocalDateTime.of(2020, 3, 1, 0, 0)).build();
        e3 = Exchange.builder()
                .paymentId(3L).myId("chong").otherId("kakao").exchangeType(Exchange.EXCHANGE_TYPE.PAYMENT)
                .money(9000L).exchangeDate(LocalDateTime.of(2020, 5, 1, 0, 0)).build();
        e4 = Exchange.builder()
                .paymentId(4L).myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
                .money(-3000L).exchangeDate(LocalDateTime.of(2020, 2, 1, 0, 0)).isComplete(true).build();
        e5 = Exchange.builder()
                .paymentId(5L).myId("chong").otherId("woo").exchangeType(Exchange.EXCHANGE_TYPE.SEND)
                .money(6000L).exchangeDate(LocalDateTime.of(2020, 4, 1, 0, 0)).isComplete(false).build();
    }


    @Test
    void showUserInfo() throws Exception {
        given(this.defaultController.showUserInfo("chong"))
                .willReturn(new ResponseEntity<>(payUser, HttpStatus.OK));

        mockMvc.perform(get("/user/{id}", "chong"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showUserExchangesList() throws Exception {
        List<Exchange> exchanges = new ArrayList<>(Arrays.asList(e1, e2, e3, e4, e5));

        given(this.defaultController.showUserExchangesList("chong"))
                .willReturn(new ResponseEntity<>(exchanges, HttpStatus.OK));

        mockMvc.perform(get("/user/exchanges/all/{id}", "chong"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showUserPaymentsList() throws Exception {
        List<Exchange> exchanges = new ArrayList<>(Arrays.asList(e2, e3));

        given(this.defaultController.showUserPaymentsList("chong"))
                .willReturn(new ResponseEntity<>(exchanges, HttpStatus.OK));

        mockMvc.perform(get("/user/exchanges/payment/{id}", "chong"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showUserSendsList() throws Exception {
        List<Exchange> exchanges = new ArrayList<>(Arrays.asList(e1, e4, e5));

        given(this.defaultController.showUserSendsList("chong"))
                .willReturn(new ResponseEntity<>(exchanges, HttpStatus.OK));

        mockMvc.perform(get("/user/exchanges/send/{id}", "chong"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showUserOnlySendList() throws Exception {
        List<Exchange> exchanges = new ArrayList<>(Collections.singletonList(e4));

        given(this.defaultController.showUserOnlySendList("chong"))
                .willReturn(new ResponseEntity<>(exchanges, HttpStatus.OK));

        mockMvc.perform(get("/user/exchanges/send/to/{id}", "chong"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showUserOnlyReceiveList() throws Exception{
        List<Exchange> exchanges = new ArrayList<>(Arrays.asList(e1, e5));

        given(this.defaultController.showUserOnlyReceiveList("chong"))
                .willReturn(new ResponseEntity<>(exchanges, HttpStatus.OK));

        mockMvc.perform(get("/user/exchanges/send/from/{id}", "chong"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showUserSendNotCompletedList() throws Exception{
        List<Exchange> exchanges = new ArrayList<>(Arrays.asList(e1, e5));

        given(this.defaultController.showUserSendNotCompletedList("chong"))
                .willReturn(new ResponseEntity<>(exchanges, HttpStatus.OK));

        mockMvc.perform(get("/user/exchanges/send/not-completed/{id}", "chong"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void registerCard() throws Exception{
        CardRegister cardRegister = CardRegister.builder()
                .cardNum("1111").yaer(20).month(1).cvc(111).cardCompany("BD").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String object = objectMapper.writeValueAsString(cardRegister);

        given(this.defaultController.registerCard("chong", cardRegister))
                .willReturn(new ResponseEntity<>("good", HttpStatus.OK));

        mockMvc.perform(post("/user/register/card/{id}", "chong")
                            .content(object)
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void registerBankAccount() throws Exception{
        BankRegister helloBank = BankRegister.builder()
                .bankName("HELLO").accountNo("12345678").build();
        ObjectMapper mapper = new ObjectMapper();
        String object = mapper.writeValueAsString(helloBank);

        given(this.defaultController.registerBankAccount("chong", helloBank))
                .willReturn(new ResponseEntity<>("good", HttpStatus.OK));

        mockMvc.perform(post("/user/register/bank/{id}", "chong")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void chargeMoneyByCard() throws Exception{
        long money = 1000L;
        ObjectMapper mapper = new ObjectMapper();
        String object = mapper.writeValueAsString(money);

        given(this.defaultController.chargeMoneyByCard("chong", money))
                .willReturn(new ResponseEntity<>("good", HttpStatus.OK));

        mockMvc.perform(post("/user/charge/card/{id}", "chong")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void chargeMoneyByBank() throws Exception{
        long money = 1000L;
        ObjectMapper mapper = new ObjectMapper();
        String object = mapper.writeValueAsString(money);

        given(this.defaultController.chargeMoneyByBank("chong", money))
                .willReturn(new ResponseEntity<>("good", HttpStatus.OK));

        mockMvc.perform(post("/user/charge/bank/{id}", "chong")
                .content(object)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void showShopInfo() throws Exception{
        Shop shop = Shop.builder().shopId("home").payMoney(1000L).build();

        given(this.defaultController.showShopInfo("home"))
                .willReturn(new ResponseEntity<>(shop, HttpStatus.OK));

        mockMvc.perform(get("/shop/{id}", "chong"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}