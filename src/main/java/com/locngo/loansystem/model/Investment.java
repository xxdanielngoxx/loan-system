package com.locngo.loansystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "investments")
public class Investment implements Serializable {

    private static final long serialVersionUID = -6317041907374159283L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "investment_sequence")
    @SequenceGenerator(sequenceName = "investment_sequence", name = "investment_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_id")
    private Lender lender;

    @CreatedDate
    @JsonProperty("created_date")
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @CreatedBy
    @JsonProperty("created_by")
    @Column(name = "created_by")
    private String createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Lender getLender() {
        return lender;
    }

    public void setLender(Lender lender) {
        this.lender = lender;
    }
}
