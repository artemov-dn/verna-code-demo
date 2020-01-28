package com.example.demo.controllers;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.dto.StatisticDTO;
import com.example.demo.service.AgreementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AgreementService agreementService;

    @Test
    void getAgreements() throws Exception {
        StatisticDTO items = new StatisticDTO.Builder()
                .count(0)
                .minAmount(new BigDecimal("0"))
                .maxAmount(new BigDecimal("0"))
                .sum(new BigDecimal("0"))
                .build();
        Mockito.when(agreementService.getStatistics(anyInt(),anyInt())).thenReturn(items);
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/statistics?clientId=211&productId=212"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/statistics?clientId=abc&productId=212"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
