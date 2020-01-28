package com.example.demo.controllers;

import com.example.demo.dto.AgreementDTO;
import com.example.demo.service.AgreementService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@WebMvcTest(AgreementController.class)
class AgreementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AgreementService agreementService;

    @Test
    public void deleteNotExistAgreement() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/agreements/10000")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void deleteAgreement() throws Exception{
        AgreementDTO agreement = new AgreementDTO.Builder()
                .agreementId(10)
                .clientId(211)
                .productId(212)
                .amount(new BigDecimal("112.34"))
                .startDate(new Date(1579564800L * 1000))
                .timestamp(new Date(1579545850L * 1000))
                .build();
        when(agreementService.deleteAgreement(10)).thenReturn(agreement);

        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/agreements/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void getAllAgreements() throws Exception {
        List<AgreementDTO> items = new ArrayList<>();
        items.add(new AgreementDTO.Builder().clientId(211).productId(212).amount(new BigDecimal("112.34")).startDate(new Date(1579564800L * 1000)).build());
        when(agreementService.getAgreements(null, null)).thenReturn(items);

        Mockito.when(agreementService.getAgreements(null,null)).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/agreements"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void getAgreements() throws Exception {
        List<AgreementDTO> items = new ArrayList<>();
        Mockito.when(agreementService.getAgreements(any(),any())).thenReturn(items);
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/agreements?clientId=211&productId=212"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void getAgreementsShouldReturnEmptyList() throws Exception {
        List<AgreementDTO> items = new ArrayList<>();
        when(agreementService.getAgreements(null, null)).thenReturn(items);
        this.mockMvc.perform(get("/rest/agreements"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }



    @Test
    public void getAgreement() throws Exception {
        AgreementDTO agreement = new AgreementDTO.Builder()
                .agreementId(10)
                .clientId(211)
                .productId(212)
                .amount(new BigDecimal("112.34"))
                .startDate(new Date(1579564800L * 1000))
                .timestamp(new Date(1579545850L * 1000))
                .build();
        when(agreementService.getAgreement(10)).thenReturn(agreement);

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/agreements/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.agreementId", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId", Matchers.is(211)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId",Matchers.is(212)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount",Matchers.is("112.34")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate",Matchers.is("2020-01-21")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp",Matchers.is("2020-01-20T21:44:10.0")));
        verify(agreementService).getAgreement(10);
    }


    @Test
    void getNotExistAgreement() throws Exception {
        when(agreementService.getAgreement(10)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/agreements/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addAgreements() throws Exception {
        String jsonString = "{\n" +
                "\"clientId\":311,\n" +
                "\"productId\":218,\n" +
                "\"amount\":\"112.34\",\n" +
                "\"startDate\":\"2020-01-21\"\n" +
                "}";
        AgreementDTO agreement = new AgreementDTO.Builder()
                .agreementId(10)
                .clientId(311)
                .productId(218)
                .amount(new BigDecimal("112.34"))
                .startDate(new Date(1579564800L * 1000))
                .timestamp(new Date(1579545850L * 1000))
                .build();
        doReturn(agreement).when(agreementService).addAgreement(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.agreementId", Matchers.is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId", Matchers.is(311)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId",Matchers.is(218)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount",Matchers.is("112.34")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate",Matchers.is("2020-01-21")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp",Matchers.is("2020-01-20T21:44:10.0")))
                .andReturn();
    }

    @Test
    void addAgreementWithNullProperty() throws Exception {
        String jsonString = "{\"productId\":218, \"amount\":\"112.34\", \"startDate\":\"2020-01-21\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        jsonString = "{\"clientId\":311, \"amount\":\"112.34\", \"startDate\":\"2020-01-21\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        jsonString = "{\"clientId\":311, \"productId\":218, \"startDate\":\"2020-01-21\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        jsonString = "{\"clientId\":311, \"productId\":218, \"amount\":\"112.34\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void addAgreementWithNegativeOrZeroAmount() throws Exception {
        String jsonString = "{\"clientId\":311, \"productId\":218, \"amount\":\"0\", \"startDate\":\"2020-01-21\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        jsonString = "{\"clientId\":311, \"productId\":218, \"amount\":\"-100.0\", \"startDate\":\"2020-01-21\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void addAgreementWithErrorJSON() throws Exception {
        String jsonString = "{\"clientId\":311, \"productId\": \"amount\":\"112.34\", \"startDate\":\"2020-01-21\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        jsonString = "{\"clientId\":311, productId:218, \"amount\":\"112.34\", \"startDate\":\"2020-01-21\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/agreements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}