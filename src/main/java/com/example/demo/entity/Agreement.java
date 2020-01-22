package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by den on 21.01.20.
 */
public class Agreement {
    private Integer clientId;
    private Integer productId;
    private BigDecimal amount;
    private Date startDate;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
