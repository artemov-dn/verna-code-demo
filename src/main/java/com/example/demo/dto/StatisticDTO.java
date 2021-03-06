package com.example.demo.dto;

import com.example.demo.serializer.CustomBigDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by den on 21.01.20.
 */
public class StatisticDTO {
    private final Integer count;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private final BigDecimal minAmount;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private final BigDecimal maxAmount;
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private final BigDecimal sum;

    private StatisticDTO(StatisticDTO.Builder builder) {
        this.count = builder.count;
        this.minAmount = builder.minAmount;
        this.maxAmount = builder.maxAmount;
        this.sum = builder.sum;
    }

    @Schema(description = "Количество договоров")
    public Integer getCount() {
        return count;
    }

    @Schema(description = "Минимальная сумма договор")
    public BigDecimal getMinAmount() {
        return minAmount;
    }

    @Schema(description = "Максимальная сумма договор")
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    @Schema(description = "Сумма всех договоров")
    public BigDecimal getSum() {
        return sum;
    }

    public static class Builder {
        private Integer count;
        private BigDecimal minAmount;
        private BigDecimal maxAmount;
        private BigDecimal sum;

        public StatisticDTO.Builder count(Integer count) {
            this.count = count;
            return this;
        }

        public StatisticDTO.Builder minAmount(BigDecimal minAmount) {
            this.minAmount = minAmount;
            return this;
        }

        public StatisticDTO.Builder maxAmount(BigDecimal maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public StatisticDTO.Builder sum(BigDecimal sum) {
            this.sum = sum;
            return this;
        }

        public StatisticDTO build(){
            return new StatisticDTO(this);
        }
    }

}
