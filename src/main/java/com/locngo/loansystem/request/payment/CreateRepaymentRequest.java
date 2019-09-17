package com.locngo.loansystem.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CreateRepaymentRequest {

    @JsonProperty("payment_id")
    private Long paymentId;

    @JsonProperty("payment_date")
    private LocalDateTime paymentDate;

    private Double amount;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
