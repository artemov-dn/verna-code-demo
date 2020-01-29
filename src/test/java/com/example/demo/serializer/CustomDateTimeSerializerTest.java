package com.example.demo.serializer;

import com.example.demo.dto.AgreementDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomDateTimeSerializerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerializingTimestampWithJsonSerialize() throws JsonProcessingException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.SSS");
        String datetime = "21.01.2020 10:15:20.125";
        Date timestamp = simpleDateFormat.parse(datetime);
        AgreementDTO dto = new AgreementDTO.Builder()
                .agreementId(1)
                .timestamp(timestamp)
                .build();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        assertTrue(jsonString.contains("\"timestamp\" : \"2020-01-21T10:15:20.125\""));
    }
}