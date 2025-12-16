package com.example.payment.controller;

import com.example.payment.entity.PaymentStatus;
import com.example.payment.entity.PaymentStatusEnum;
import com.example.payment.service.PaymentStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@org.springframework.boot.test.context.SpringBootTest
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
public class PaymentStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentStatusService service;

    @Test
    public void getPaymentStatus_ShouldReturnStatus_WhenExists() throws Exception {
        String orderId = "1";
        // Data is seeded by CommandLineRunner on startup

        mockMvc.perform(get("/payments/{orderId}/status", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.paymentStatus").value("SUCCESS"));
    }

    @Test
    public void getPaymentStatus_ShouldReturn404_WhenNotFound() throws Exception {
        String orderId = "999";

        mockMvc.perform(get("/payments/{orderId}/status", orderId))
                .andExpect(status().isNotFound());
    }
}
