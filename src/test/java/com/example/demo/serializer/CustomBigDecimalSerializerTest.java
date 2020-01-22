package com.example.demo.serializer;

import com.example.demo.dto.AgreementDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class CustomBigDecimalSerializerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerializingStartDateWithJsonSerialize() throws JsonProcessingException, ParseException {
        AgreementDTO dto = new AgreementDTO.Builder()
                .agreementId(1)
                .amount(new BigDecimal("113.12"))
                .build();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
//        System.out.println(jsonString);
        assertTrue(jsonString.contains("\"amount\" : \"113.12\""));

    }

}