package com.locngo.loansystem.request.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTransactionRequest {

    @JsonProperty("loan_id")
    private Long loanId;

    @JsonProperty("borrower_id")
    private Long borrowerId;

    private String description;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
