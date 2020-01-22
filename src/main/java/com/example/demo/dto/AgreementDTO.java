package com.example.demo.dto;

import com.example.demo.serializer.CustomBigDecimalSerializer;
import com.example.demo.serializer.CustomDateSerializer;
import com.example.demo.serializer.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by den on 21.01.20.
 */
public class AgreementDTO {
    private final Integer agreementId;
    private final Integer clientId;
    private final Integer productId;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private final BigDecimal amount;
    @JsonSerialize(using = CustomDateSerializer.class)
    private final Date startDate;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private final Date timestamp;

    private AgreementDTO(AgreementDTO.Builder builder) {
        this.agreementId = builder.agreementId;
        this.clientId = builder.clientId;
        this.productId = builder.productId;
        this.amount = builder.amount;
        this.startDate = builder.startDate;
//        this.timestamp = LocalDateTime.now().toString();
        this.timestamp = builder.timestamp;
//        this.timestamp = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Integer getAgreementId() {
        return agreementId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public BigDecimal getAmount() { return amount;   }

    public Date getStartDate() {
        return startDate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public static class Builder {
        private Integer agreementId;
        private Integer clientId;
        private Integer productId;
        private BigDecimal amount;
        private Date startDate;
        private Date timestamp;

        public AgreementDTO.Builder agreementId(Integer agreemenId) {
            this.agreementId = agreemenId;
            return this;
        }

        public AgreementDTO.Builder clientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public AgreementDTO.Builder productId(Integer productId) {
            this.productId = productId;
            return this;
        }

        public AgreementDTO.Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public AgreementDTO.Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public AgreementDTO.Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AgreementDTO build(){
            return new AgreementDTO(this);
        }
    }
}
