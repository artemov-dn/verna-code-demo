package com.example.demo.serializer;

import com.example.demo.dto.AgreementDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class CustomDateSerializerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerializingStartDateWithJsonSerialize() throws JsonProcessingException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = "21.01.2020";
        Date startDate = simpleDateFormat.parse(date);

        AgreementDTO dto = new AgreementDTO.Builder()
                .agreementId(1)
                .startDate(startDate)
                .build();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        assertTrue(jsonString.contains("\"startDate\" : \"2020-01-21\""));

    }
}