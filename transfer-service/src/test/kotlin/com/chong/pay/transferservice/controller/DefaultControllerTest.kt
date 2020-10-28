package com.chong.pay.transferservice.controller

import com.chong.pay.transferservice.domain.Exchange
import com.chong.pay.transferservice.domain.TransferRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import java.time.LocalDateTime

@WebMvcTest(value = [DefaultController::class])
internal class DefaultControllerTest {

    companion object{
        private val log = LoggerFactory.getLogger(DefaultControllerTest::class.java)
    }

    @Autowired
    lateinit var mockMvc: MockMvc
    @MockBean
    lateinit var defaultController: DefaultController

    @Test
    fun transferRequest() {
        val paymentRequest = TransferRequest("c1", "c2", 100)
        val paymentResult = Exchange()

        given(defaultController.transferRequest(paymentRequest))
                .willReturn(ResponseEntity(paymentResult, HttpStatus.OK))

        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        val stringRequest = objectMapper.writeValueAsString(paymentRequest)

        mockMvc.perform(post("/transfer").contentType(MediaType.APPLICATION_JSON).content(stringRequest))
                .andExpect(status().isOk())
                .andDo(print())
    }

    @Test
    fun decideTransfer() {
        val paymentId = "1"

        given(defaultController.decideTransfer(paymentId))
                .willReturn(ResponseEntity("ok", HttpStatus.OK))

        val objectMapper = ObjectMapper()

        mockMvc.perform(post("/transfer/complete/" + paymentId))
                .andExpect(status().isOk)
                .andDo(print())
    }
}