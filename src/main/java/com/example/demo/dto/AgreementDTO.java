package com.example.demo.dto;

import com.example.demo.serializer.CustomBigDecimalSerializer;
import com.example.demo.serializer.CustomDateSerializer;
import com.example.demo.serializer.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
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
@JsonDeserialize(builder = AgreementDTO.Builder.class)
public class AgreementDTO {
    private final Integer agreementId;
    @NotBlank
    private final Integer clientId;
    @NotBlank
    private final Integer productId;
    @NotBlank
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private final BigDecimal amount;
    @NotBlank
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
        this.timestamp = builder.timestamp;
    }

    @Schema(example = "3453", description = "ИД договора")
    public Integer getAgreementId() {
        return agreementId;
    }

    @Schema(example = "278", description = "ИД клиента")
    public Integer getClientId() {
        return clientId;
    }

    @Schema(example = "14", description = "ИД продукта")
    public Integer getProductId() {
        return productId;
    }

    @Schema(example = "123.45", description = "Сумма договора. Должна быть больше ноля.")
    public BigDecimal getAmount() { return amount;   }

    @Schema(description = "Дата начала действия договора")
    public Date getStartDate() {
        return startDate;
    }

    @Schema(description = "Дата время создания договора")
    public Date getTimestamp() {
        return timestamp;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private Integer agreementId;
        private Integer clientId;
        private Integer productId;
        private BigDecimal amount;
        private Date startDate;
        private Date timestamp;

        public Builder agreementId(Integer agreemenId) {
            this.agreementId = agreemenId;
            return this;
        }

        public Builder clientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder productId(Integer productId) {
            this.productId = productId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AgreementDTO build(){
            return new AgreementDTO(this);
        }
    }
}
