package com.chong.pay.paymentservice.controller;

import com.chong.pay.paymentservice.domain.PaymentRequest;
import com.chong.pay.paymentservice.domain.PaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PaymentController controller;

    @Test
    void doPayment() throws Exception {
        PaymentRequest request = PaymentRequest.builder()
                .userId("chong").shopId("1111").money(10000).dateTime(LocalDateTime.now())
                .doesUsePoint(true).usingPoint(100).build();
        PaymentResponse response = PaymentResponse.builder()
                .userId("chong").shopName("home").money(10000).build();

        given(this.controller.doPayment(request))
                .willReturn(new ResponseEntity<>(response, HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String object = mapper.writeValueAsString(request);

        System.out.println(object);

        mockMvc.perform(post("/payment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(object))
                .andExpect(status().isOk())
                .andDo(print());
    }
}