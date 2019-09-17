package com.locngo.loansystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "loans")
public class Loan implements Serializable {

    private static final long serialVersionUID = 6979929817457695066L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_sequence")
    @SequenceGenerator(sequenceName = "loan_sequence", name = "loan_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "fund")
    private Double fund;

    @Column(name = "period")
    private Integer period;

    @JsonProperty("base_rate")
    @Column(name = "base_rate")
    private Double baseRate;

    @Column(name = "description")
    private String description;

    @Embedded
    private BaseEntity baseEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_id")
    private Lender lender;

    @OneToMany(mappedBy = "loan")
    private Set<Transaction> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFund() {
        return fund;
    }

    public void setFund(Double fund) {
        this.fund = fund;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Lender getLender() {
        return lender;
    }

    public void setLender(Lender lender) {
        this.lender = lender;
    }

    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
