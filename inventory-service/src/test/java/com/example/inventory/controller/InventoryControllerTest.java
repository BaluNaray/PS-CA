package com.example.inventory.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getInvoiceItems_ShouldReturnItems_WhenExists() throws Exception {
        Long orderId = 1L;
        // Data is seeded by CommandLineRunner on startup for Order 1

        mockMvc.perform(get("/api/v1/orders/{orderId}/invoice-items", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].parcelId").value(100001))
                .andExpect(jsonPath("$[1].orderId").value(1))
                .andExpect(jsonPath("$[1].parcelId").value(100002));
    }

    @Test
    public void getInvoiceItems_ShouldReturnEmptyList_WhenNoItems() throws Exception {
        Long orderId = 999L;

        mockMvc.perform(get("/api/v1/orders/{orderId}/invoice-items", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
