package com.locngo.loansystem.request.loan;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoanCreateRequest {

    private String name;

    private Double fund;

    private Integer period;

    @JsonProperty("base_rate")
    private Double baseRate;

    private String description;

    public Double getFund() {
        return fund;
    }

    public void setFund(Double fund) {
        this.fund = fund;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
